package path

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.stewsters.path.ecs.component.Armor
import org.junit.Test


class SerializationTests {

    @Test
    fun testComponentSerialization() {

        val armor = Armor(5)
        val output = jacksonObjectMapper().writeValueAsString(armor)

        assert(output == "{\"armor\":5}")
    }

    @Test
    fun testComponentDeserialization() {

        val armor: Armor = jacksonObjectMapper().readValue("{\"armor\":5}")

        assert(armor.armor == 5)
    }
}