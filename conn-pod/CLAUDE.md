# Create: Shatterdome — working rules

## Always ask when unsure
If a requirement, design choice, API behaviour or file location is not clear, **ask the user a question
instead of guessing**. Prefer a few short, concrete questions before writing code.

## Project
- NeoForge 1.21.1, Create 6.0.x addon, mod id `shatterdome`, package `com.shatterdome`.
- Registration goes through `Shatterdome.REGISTRATE` (Create's Registrate). Blocks in `AllBlocks`,
  block entities in `AllBlockEntityTypes`, entities in `AllEntityTypes`.
- Block models are hand-built boxes in `content/util/ShatterdomeModels`; run `./gradlew runData` after
  changing blocks/lang.
- Client-only code lives in `client/` and uses `@EventBusSubscriber(..., value = Dist.CLIENT)`.

## Conn Pod (v1 decisions)
One pilot, Create seat entity (block extends `SeatBlock`), horizontal facing, mouse looks normally and
holding the Limb Mode key switches it to limb control, rebindable keys under "Create: Shatterdome".
