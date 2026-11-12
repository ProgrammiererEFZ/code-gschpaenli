package io.github.programmiererefz.codegschpaenli.overlay;

import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import io.github.programmiererefz.codegschpaenli.world.Ball;
import org.junit.jupiter.api.Test;

import javax.swing.JRootPane;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Renders the overlay into an image that stands in for the screen: an area only changes when the overlay repaints it
 * (its dirty regions) or when the IDE repaints something there, exactly like Swing. Then counts the balls on it.
 */
class PetOverlayBallTest {

    private static final int W = 800;
    private static final int H = 600;
    private static final Color BACKGROUND = new Color(0x2B2D30);

    private final BufferedImage screen = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    private PetOverlay overlay;

    private PetOverlay newOverlay() {
        JRootPane rootPane = new JRootPane();
        rootPane.setSize(W, H);
        rootPane.getLayeredPane().setBounds(0, 0, W, H);
        PetOverlay o = new PetOverlay(rootPane, 7, new PetOverlays(() -> { }));
        o.regionPainter = this::paintRegion;
        o.applySettings(new PetsSettings.StateBean(), PetsSettings.Size.SMALL);
        o.world().setSurfaces(List.of()); // just the window bottom
        return o;
    }

    /** What Swing does for a repaint of {@code r}: everything underneath, then the overlay on top, clipped to r. */
    private void paintRegion(Rectangle r) {
        Graphics2D g = screen.createGraphics();
        try {
            g.setClip(r);
            g.setColor(BACKGROUND);
            g.fill(r);
            overlay.paintComponent(g);
        } finally {
            g.dispose();
        }
    }

    @Test
    void throwingAnotherBallWhilePausedDoesNotLeaveTheOldBallOnScreen() {
        overlay = newOverlay();
        paintRegion(new Rectangle(0, 0, W, H));

        // throw a ball and let it fly and roll for five seconds of running animation
        overlay.throwBall();
        for (int i = 0; i < 300; i++) {
            overlay.world().tick(1.0 / 60);
            overlay.repaintDirty();
        }
        Ball first = overlay.world().ball();
        assertNotNull(first);
        assertTrue(first.y > H - 50, "first ball should be down at the window bottom, y=" + first.y);
        assertEquals(1, countBalls(), "one ball on screen while it is animating");

        // The window is paused (e.g. the Find Action popup has focus, so the IDE frame is inactive) and the user
        // throws again. No tick runs, but the IDE keeps repainting parts of the window, here the top strip where
        // the new ball starts (the area under the closing popup, a toolbar button...).
        overlay.throwBall();
        Ball second = overlay.world().ball();
        assertTrue(second.y < 120, "new ball starts near the top, y=" + second.y);
        paintRegion(new Rectangle(0, 0, W, 120));

        assertEquals(1, countBalls(), "the old ball must be gone once a new one is thrown");
    }

    /** Number of separate red blobs on the screen. */
    private int countBalls() {
        boolean[] red = new boolean[W * H];
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int rgb = screen.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
                red[y * W + x] = r > 100 && r - Math.max(g, b) > 40;
            }
        }
        boolean[] seen = new boolean[W * H];
        int blobs = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int start = 0; start < red.length; start++) {
            if (!red[start] || seen[start]) continue;
            blobs++;
            seen[start] = true;
            queue.add(start);
            while (!queue.isEmpty()) {
                int p = queue.poll();
                int px = p % W, py = p / W;
                // pixels up to 3 apart belong together, so the light stripe does not split a ball in two
                for (int dy = -3; dy <= 3; dy++) {
                    for (int dx = -3; dx <= 3; dx++) {
                        int x = px + dx, y = py + dy;
                        if (x < 0 || y < 0 || x >= W || y >= H) continue;
                        int q = y * W + x;
                        if (red[q] && !seen[q]) {
                            seen[q] = true;
                            queue.add(q);
                        }
                    }
                }
            }
        }
        return blobs;
    }
}
