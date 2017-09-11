package com.stewsters.path.screen

import com.valkryst.VTerminal.Panel
import com.valkryst.VTerminal.builder.component.ButtonBuilder
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.component.Button
import com.valkryst.VTerminal.component.Screen

class MainMenuScreen(panel: Panel, screenBuilder: ScreenBuilder) : Screen(screenBuilder) {
    val buttonNewGame: Button
    val buttonExitGame: Button

    init {

        // Construct menu options:
        val builder = ButtonBuilder()
        builder.text = "New Game"
        builder.radio = panel.radio
        builder.columnIndex = panel.widthInCharacters / 3
        builder.rowIndex = panel.heightInCharacters / 3
        buttonNewGame = builder.build()

        builder.text = "Exit"
        builder.rowIndex = builder.rowIndex + 1
        buttonExitGame = builder.build()

        // Swap Screen:
        panel.swapScreen(this)

        buttonNewGame.setOnClickFunction({
            // Initialize world tiles:
            val gameScreen = GameScreen(panel, screenBuilder)

            panel.swapScreen(gameScreen)

//            val tiles = gameScreen.world.getCurrentMap()
//
//            for (x in tiles.indices) {
//                for (y in 0..tiles[x].size - 1) {
//                    tiles[x][y].placeOnScreen(panel.screen, x, y)
//                }
//            }

            // Initialize entities:
//            val player = Entity(
//                    pos = Point2i(25,12)
//                    //race:Race.HUMAN
//                    )

//            val playerBuilder = PlayerBuilder()
//            playerBuilder.setX(25)
//            playerBuilder.setY(12)
//            playerBuilder.setRace(Race.HUMAN)
//            val player = playerBuilder.build()

//            val weaponBuilder = WeaponBuilder()
//            weaponBuilder.setName("TWep")
//            weaponBuilder.setDescription("DoTWep")
//            weaponBuilder.setSlot(EquipmentSlot.MAIN_HAND)

//            player.getEquipment().setItemInSlot(EquipmentSlot.MAIN_HAND, weaponBuilder.build())
//            player.getActions().add(UpdateLOSAction(25, 12, 0, 0))


//            val creatureBuilder = CreatureBuilder()
//            creatureBuilder.setX(26)
//            creatureBuilder.setY(13)
//            creatureBuilder.setRace(Race.HUMAN)
//            creatureBuilder.setCombatAI(AggressiveCombatAI())
//            creatureBuilder.setSprite(Sprite.ENEMY)
//            val npc = creatureBuilder.build()
//            npc.getEquipment().setItemInSlot(EquipmentSlot.MAIN_HAND, weaponBuilder.build())

//            gameScreen.world.addEntities(player, npc)

            // Create rooms:
//            val roomA = Rect(20, 5, 10, 15)
//            val roomB = Rect(50, 5, 10, 15)
//            roomA.carve(gameScreen)
//            roomB.carve(gameScreen)
        })
        buttonExitGame.setOnClickFunction({ System.exit(0) })

        // Add components to Screen VIA Panel functions:
        panel.addComponents(buttonNewGame, buttonExitGame)

    }

}
