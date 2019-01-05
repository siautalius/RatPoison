

package rat.poison.game

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.Player
import rat.poison.game.entity.position
import rat.poison.game.entity.punch
import rat.poison.game.netvars.NetVarOffsets.vecViewOffset
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.normalize
import java.lang.Math.atan
import java.lang.Math.toDegrees

private val angles: ThreadLocal<Angle> = ThreadLocal.withInitial { Vector() }

fun calculateAngle(player: Player, dst: Vector): Angle = angles.get().apply {
	val myPunch = player.punch()
	val myPosition = player.position()
	
	val dX = myPosition.x - dst.x
	val dY = myPosition.y - dst.y
	val dZ = myPosition.z + csgoEXE.float(player + vecViewOffset) - dst.z
	
	val hyp = Math.sqrt((dX * dX) + (dY * dY))
	
	x = toDegrees(atan(dZ / hyp)) - myPunch.x * 2.0
	y = toDegrees(atan(dY / dX)) - myPunch.y * 2.0
	z = 0.0
	if (dX >= 0.0) y += 180
	
	normalize()
}