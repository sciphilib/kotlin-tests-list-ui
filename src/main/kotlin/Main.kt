package org.example
import org.jetbrains.annotations.TestOnly
import ui.ListManagerFrame
import javax.swing.SwingUtilities
import org.junit.*
import org.junit.jupiter.api.Test


fun main() {

    SwingUtilities.invokeLater { ListManagerFrame<Any>() }
}