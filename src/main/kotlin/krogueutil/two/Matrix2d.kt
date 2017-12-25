package krogueutil.two


class Matrix2d<T>(val xSize: Int, val ySize: Int, val data: Array<T>) {

    operator fun get(p: Vec2): T = get(p.x, p.y)

    operator fun get(x: Int, y: Int): T {
        return data[x + y * xSize]
    }

    operator fun set(p: Vec2, value: T) = set(p.x, p.y, value)

    operator fun set(x: Int, y: Int, value: T) {
        data[x + y * xSize] = value
    }
}

inline fun <reified T> Matrix2d(xSize: Int, ySize: Int, init: (Int, Int) -> T) =
        Matrix2d(xSize, ySize, Array(xSize * ySize, { i -> init(i % xSize, i / xSize) }))