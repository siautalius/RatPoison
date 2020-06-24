package rat.poison.ui.uiHelpers.binds

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisValidatableTextField
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed

class ConcatedInputField(firstLocaleName: String, secondLocaleName: String, varName: String, addLink: Boolean = true, isInt: Boolean = true) : VisTable() {
    private val defaultText = curLocalization[firstLocaleName] + " " + curLocalization[secondLocaleName]
    var variableName = varName
    private val intVal = isInt
    private val firstLocaleName = firstLocaleName
    private val secondLocaleName = secondLocaleName
    //val globalTable = VisTable()
    private var keyLabel = VisLabel("$defaultText:")
    private val keyField = if (isInt) { VisValidatableTextField(Validators.INTEGERS) } else { VisValidatableTextField(Validators.FLOATS) }
    private val linkLabel = LinkLabel("?", "http://cherrytree.at/misc/vk.htm")

    var value: Any = 0

    init {
        update()

        changed { _, _ ->
            if (keyField.text.toIntOrNull() != null) {
                if (keyField.text != curSettings[variableName]) {
                    if (intVal) {
                        curSettings[variableName] = keyField.text.toInt().toString()
                        value = keyField.text.toInt()
                    } else {
                        curSettings[variableName] = keyField.text.toDouble().toString()
                        value = keyField.text.toDouble()
                    }
                }
            }

            false
        }

        add(keyLabel).width(200F).spaceRight(80f)
        add(keyField).spaceRight(6F).width(50F)
        if (addLink) {
            add(linkLabel)
        }
    }

    fun update(neglect: Actor? = null) {
        if (neglect != this) {
            val tmpText = curLocalization[firstLocaleName] + " " + curLocalization[secondLocaleName]
            this.keyLabel.setText(if (tmpText.isBlank()) defaultText else tmpText)
            if (!curSettings[variableName].isBlank()) {
                keyField.text = curSettings[variableName]
                value = if (intVal) {
                    keyField.text.toInt()
                } else {
                    keyField.text.toDouble()
                }
            }
            else {
                keyField.text = "22"
            }
        }
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        keyField.isDisabled = bool
    }
}