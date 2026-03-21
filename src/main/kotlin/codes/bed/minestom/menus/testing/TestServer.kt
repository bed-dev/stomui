package codes.bed.minestom.menus.testing

import codes.bed.minestom.menus.menu
import codes.bed.minestom.menus.StomUI
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.inventory.InventoryType
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Material
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("StomUITestServer")

fun main() {
    val minecraftServer = MinecraftServer.init()
    val globalEventHandler = MinecraftServer.getGlobalEventHandler()
    val instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer()

    StomUI.init(globalEventHandler)

    // Build a simple stone platform at spawn so local testing is immediate.
    for (x in -6..6) {
        for (z in -6..6) {
            instanceContainer.setBlock(Pos(x.toDouble(), 40.0, z.toDouble()), Block.STONE)
        }
    }

    // Add bright reference points so the test area stays visible.
    listOf(
        Pos(0.0, 40.0, 0.0),
        Pos(-4.0, 40.0, -4.0),
        Pos(-4.0, 40.0, 4.0),
        Pos(4.0, 40.0, -4.0),
        Pos(4.0, 40.0, 4.0)
    ).forEach { instanceContainer.setBlock(it, Block.SEA_LANTERN) }

    registerMenuCommands()

    globalEventHandler.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
        event.spawningInstance = instanceContainer
        event.player.respawnPoint = Pos(0.5, 42.0, 0.5)
        logger.info("Configuring player {}", event.player.username)
    }

    globalEventHandler.addListener(PlayerSpawnEvent::class.java) { event ->
        val player = event.player
        player.gameMode = GameMode.CREATIVE

        if (event.isFirstSpawn) {
            player.sendMessage(Component.text("StomUI test menus:"))
            player.sendMessage(Component.text("/menu-basic - simple inventory menu"))
            player.sendMessage(Component.text("/menu-filler - menu with filler pane layout"))
            player.sendMessage(Component.text("/menu-live - menu that updates in-place"))
            logger.info("Player {} spawned in creative", player.username)
        }
    }

    globalEventHandler.addListener(PlayerDisconnectEvent::class.java) { event ->
        logger.info("Player {} disconnected", event.player.username)
    }

    logger.info("Starting StomUI test server on 0.0.0.0:25565")
    minecraftServer.start("0.0.0.0", 25565)
}

private fun registerMenuCommands() {
    val commandManager = MinecraftServer.getCommandManager()

    commandManager.register(object : Command("menu-basic") {
        init {
            setDefaultExecutor { sender, _ ->
                withPlayer(sender) { player ->
                    menu(player) {
                        title("Basic Demo")
                        size(InventoryType.CHEST_3_ROW)

                        button {
                            title("<green>Accept")
                            text("Accept")
                            slots(11)
                            item(Material.LIME_DYE) {}
                            action { player.sendMessage(Component.text("Clicked Accept")) }
                        }

                        button {
                            title("<red>Decline")
                            text("Decline")
                            slots(15)
                            item(Material.RED_DYE) {}
                            action { player.sendMessage(Component.text("Clicked Decline")) }
                        }
                    }
                }
            }
        }
    })

    commandManager.register(object : Command("menu-filler") {
        init {
            setDefaultExecutor { sender, _ ->
                withPlayer(sender) { player ->
                    menu(player) {
                        title("Filler Demo")
                        size(InventoryType.CHEST_4_ROW)
                        filler()

                        button {
                            title("<gold>Center Action")
                            text("Center")
                            slots(13)
                            item(Material.NETHER_STAR) {}
                            action { player.sendMessage(Component.text("Center action triggered")) }
                        }
                    }
                }
            }
        }
    })

    commandManager.register(object : Command("menu-live") {
        init {
            setDefaultExecutor { sender, _ ->
                withPlayer(sender) { player ->
                    fun openLiveDemo(clicks: Int) {
                        menu(player) {
                            title("Live Demo (${clicks})")
                            size(InventoryType.CHEST_3_ROW)

                            button {
                                title("<yellow>Click to increment")
                                text("Increment")
                                slots(13)
                                item(Material.CLOCK) {}
                                action { openLiveDemo(clicks + 1) }
                            }
                        }
                    }

                    openLiveDemo(0)
                }
            }
        }
    })

    commandManager.register(object : Command("menus") {
        init {
            setDefaultExecutor { sender, _ ->
                sender.sendMessage(Component.text("/menu-basic, /menu-filler, /menu-live"))
            }
        }
    })
}

private fun withPlayer(sender: CommandSender, block: (Player) -> Unit) {
    if (sender !is Player) {
        sender.sendMessage(Component.text("This command can only be run by a player."))
        return
    }
    block(sender)
}

