@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1


/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 14.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {
    private var map = mutableMapOf<String, List<String>?>()

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {
        if (name in map.keys) return false
        else {
            map[name] = null
        }
        return true
    }

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean {
        if (name !in map.keys) return false
        else {
            map.remove(name)
        }
        return true
    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean {
        for ((_, value) in map) {
            if (value?.contains(phone) == true) return false
        }
        if (name !in map.keys) return false
        else {
            if (map[name] == null) map[name] = listOf(phone)
            if (map[name] != null) map[name] = map[name]?.plus(listOf(phone))
        }
        return true
    }

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {
        for ((key, value) in map) {
            if (key == name && value?.contains(phone) == false) return false
        }
        if (name !in map.keys) return false
        else {
            map[name] = map[name]?.filter { it != phone }
        }
        return true
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> {
        return if (name !in map.keys || map[name] == null) setOf()
        else map[name]!!.toSet()
    }

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        for ((key, value) in map) {
            if (value?.contains(phone) == true) return key
        }
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        val book = this.map
        var book2 = mutableMapOf<String, List<String>?>()
        if (other is PhoneBook) book2 = other.map
        else return false
        val list = mutableListOf<String>()
        for ((key, _) in book2) list.add(key)
        val result = book - list
        return result.isEmpty() && book.size == book2.size
    }

    override fun hashCode(): Int {
        var hash = 21
        hash = 31 * hash + map.size
        hash += map.forEach { it.value }.hashCode()
        hash += map.forEach { it.key }.hashCode()
        return hash
    }
}
