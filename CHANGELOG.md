<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Code Gschpänli Changelog

## [Unreleased]

### Added

- Pets that live on the IDE window itself: they walk along the window header and status bar, nap on tool windows,
  climb tool window borders, jump between surfaces and chase balls you throw.
- Two art styles: smooth cartoon pets (cat, dog, fox, bunny, panda, raccoon, hamster, pig) and pixel-art pets
  (cat, dog, duck, frog, hedgehog, penguin, mouse, turtle), each in several colours.
- Random visitors that drop by and leave again, on by default with 1 to 3 at a time.
- Right-click menu on every pet: Pet, Play Ball, Follow Me, Take a Nap / Wake Up, Stay / Free to Roam, Rename...,
  Color, Adopt (visitors) and Leave. An adopted pet that leaves comes back at the next IDE start or with Roll Call.
- Settings page with size, speed, activity, name tags, visitors and which IDE parts pets may walk on.
- New species: cartoon koala (big fluffy ears and nose), sheep (woolly fleece and cap) and lion (mane and tufted
  tail), and pixel-art owl (blinks, cocks its head, hops and flaps its wings), squirrel (bounds along in hops, sits up
  to nibble an acorn, sleeps under its bushy tail), ladybug and seal (galumphs along, belly-slides when running).

### Changed

- The plugin icon is now pixel art: the orange pixel cat peeking over an edge, paws first.

### Fixed

- Cartoon pets' near legs are drawn in front of the light belly and coat markings instead of disappearing behind them.
- Throwing a ball while the previous one was still around could show both balls at once when the animation was
  paused, e.g. because the Find Action popup had focus.
