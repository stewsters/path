package krogueutil.two

public class LosMap(val xSize: Int, val ySize: Int) {

    val map = Array<Boolean>(xSize * ySize, { i -> false });


    public fun canSee(vec: Vec2): Boolean {
        return map[vec.y * xSize + vec.x]
    }

    public fun recalcuate(){

    }

}