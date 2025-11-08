# Aurora – AI Coding Agent Quick Guide

Goal: Fast orientation to extend Aurora (Fabric 1.21.4 creative utility mod). Keep changes minimal, follow existing patterns—no speculative systems.

## Core Layout
- Entry: `AuroraMod` wires `FeatureManager`, `AuroraGui`, `AuroraKeybinds`, commands, and per‑tick fan‑out via `ClientTickEvents.END_CLIENT_TICK`.
- Features: `Feature` + `AbstractFeature` (override `onTick()` for per‑tick logic; use `onEnable()/onDisable()` to mutate state; keep null‑safe guards).
- Registry: `FeatureManager` stores features by lowercase name; adds chat feedback (guard `client.player != null`).
- Mixins (`com.aurora.mixin`): Contain almost all behavioral overrides (cooldowns, placement, collision, block updates). Feature classes are thin wrappers pointing to mixins.
- UI: `AuroraGui` draws enabled features + simple menu (defensive try/catch around render sections).
- Input: `AuroraKeybinds` (M=menu, R=Replace toggle, L=Tinker toggle).
- Commands: `/aurora list|status|toggle|enable|disable <feature>`; color formatting consistent (`§6` header, `§a/§c` state).

## Adding a Feature (Happy Path)
1. Create `NewThingFeature extends AbstractFeature` in `com.aurora.features` (constructor sets name & description).
2. Optional per‑tick logic: override `onTick()` (fast, guarded, no heavy loops). Revert any persistent changes in `onDisable()`.
3. Register inside `FeatureManager.registerFeatures()`.
4. If vanilla behavior must be intercepted, add a mixin; keep feature class comment: `// Implemented via <MixinName>`.
5. Access elsewhere: `AuroraMod.getInstance().getFeatureManager().getFeature(NewThingFeature.class)`.

## Mixin Patterns (Copy Existing Styles)
- Cooldown wipe: see `FastPlaceMixin` / `MinecraftClientMixin` (inject `tick HEAD`).
- Instant break: `FastBreakMixin` (`getBlockBreakingProgress RETURN` sets 1.0f).
- Block update freeze: `FreezeUpdatesMixin`, `ClientWorldMixin` (cancel early when feature enabled).
- Force placement: `ForcePlaceMixin` overriding `canPlace`.
- Replace interaction: `ReplaceMixin` intercepts `interactBlock` (send destroy packets then placement).
- State cycling: `TinkerMixin` on `swingHand` with empty hand.
- No‑clip: `EntityMixin`, `ClientPlayerEntityMixin` + `NoClipFeature` forcing movement flags.
Rules: inject at `HEAD` unless modifying return; always refetch feature + `isEnabled()`; mark cancellable only when needed; keep logic minimal.

## Naming & Lookup
- Feature registry key = lowercase feature name (`FastBreak` -> `fastbreak`). Avoid duplicates (silent overwrite).
- Commands expect lowercase keys (see help text inside `AuroraCommands`).

## Rendering / Safety
- Never let an exception escape GUI or tick paths—wrap where adding new logic (follow `AuroraGui`).
- Heavy world scans or multi‑block edits must NOT run every tick—gate behind explicit user action or future batching.

## Build & Run
- Java 21 toolchain via Loom. Run client: `./gradlew runClient`. Package: `./gradlew build` (jar in `build/libs/`). Version expansion comes from `gradle.properties` into `fabric.mod.json`.

## Quick Snippets
- Toggle programmatically: `AuroraMod.getInstance().getFeatureManager().toggleFeature("fastplace");`
- Fetch feature: `ReplaceFeature f = AuroraMod.getInstance().getFeatureManager().getFeature(ReplaceFeature.class);`
- Add mixin: create class, list it in `aurora.mixins.json` under `client` array, mirror guard pattern.

## When Unsure
Add a `// TODO:` with context instead of guessing (e.g., disabled screen mixins show precedent). Surface uncertainty rather than inventing APIs.

---
If something here becomes stale after refactors, update this file in the same PR.
