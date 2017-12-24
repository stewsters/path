package path

import com.stewsters.path.ecs.component.Armor
import krogueutil.JSON
import krogueutil.deserializeAs
import krogueutil.serializedAs
import org.junit.Test


class SerializationTests {

    @Test
    fun testListSerialization() {

        assert(arrayOf(1, 2, 3) serializedAs JSON == "[1,2,3]")

        val data = "[2,3,4]" deserializeAs Array<Int>::class.java

        assert(data[0] == 2)
        assert(data[1] == 3)
        assert(data[2] == 4)
    }

    @Test
    fun testComponentSerialization() {

        val armor = Armor(5)
        val output = armor serializedAs JSON
        assert(output == "{\"armor\":5}")

    }

    @Test
    fun testComponentDeserialization() {

        val armor: Armor = "{\"armor\":5}" deserializeAs Armor::class.java

        assert(armor.armor == 5)
    }
}