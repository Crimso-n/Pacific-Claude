# Create: Shatterdome (Phase 1 starter)

Pacific Rim themed Create addon. NeoForge 1.21.1, Create 6.0.x, mod id `shatterdome`.

## One-time setup (Windows / Mac / Linux)
1. Install **JDK 21** (Eclipse Temurin 21 from adoptium.net). Check with `java -version` in a terminal; it must say 21.
2. Install **IntelliJ IDEA Community** (jetbrains.com/idea).
3. Unzip this project somewhere simple (no weird characters in the path).
4. IntelliJ: File > Open > pick the `shatterdome` folder (the one with build.gradle). Choose "Trust project".
5. Wait for the Gradle sync to finish (bottom bar). The FIRST sync downloads Minecraft, NeoForge and Create and can take 5-15 minutes.
   If it complains about the JDK: File > Settings > Build Tools > Gradle > Gradle JVM = 21.

## Every time you change content
1. Run **data generation** once (creates block models, blockstates, item models, lang, loot tables):
   - IntelliJ: top-right run dropdown > `data` > green play button, OR terminal: `./gradlew runData` (Windows: `gradlew.bat runData`)
2. Run the game: run dropdown > `client`, OR `./gradlew runClient`
3. New world > Creative > open the **Create: Shatterdome** tab.

If blocks look purple/black or have no names, you skipped runData.

## What's in Phase 1
Blocks: Jaeger Controller, Limb Joint, Conn-Pod, Jaeger Alloy Plating, Weapon Mount (placeholders, no behavior yet),
Reactor Core (real Create stress source, 64 RPM, 32,768 SU), Plasma Cannon (real stress consumer, no firing yet).
Items: Jaeger Alloy Ingot, Kaiju Blue Vial, Kaiju Spawn Egg.
Fluid: Kaiju Blue (+ bucket).
Mob: Kaiju (6x-scale zombie placeholder, 600 HP, melee).
No recipes (everything is in the creative tab).

## Not done yet
Controller assembly, limb IK, Conn-Pod controls, recorded/keyframed walking, reactor fuel/heat/cooling/explosion,
cannon firing, Kaiju ranged attack + GeckoLib model, balance physics, drift/two-pilot, recipes.
