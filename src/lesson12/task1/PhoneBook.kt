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
    private val map = mutableMapOf<String, MutableList<String>>()

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {
        if (name in map.keys) return false
        else {
            map[name] = mutableListOf()
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
            if (value.contains(phone)) return false
        }
        if (name !in map.keys) return false
        else {
            map[name]?.add(phone)
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
            if (key == name && !value.contains(phone)) return false
        }
        if (name !in map.keys) return false
        else {
            map[name]?.remove(phone)
        }
        return true
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> = if (name !in map.keys || map[name]?.isEmpty() == true) setOf()
    else map[name]!!.toSet()


    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        for ((key, value) in map) {
            if (value.contains(phone)) return key
        }
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        val set = mutableSetOf<Pair<String, List<String>>>()
        val setOther = mutableSetOf<Pair<String, List<String>>>()
        if (other is PhoneBook) {
            for ((key, value) in other.map) {
                setOther.add(Pair(key, value.sorted()))
            }
        } else return false
        for ((key, value) in this.map) {
            set.add(Pair(key, value.sorted()))
        }
        return set == setOther
    }

    override fun hashCode(): Int {
        var hash = 21
        for ((key, value) in this.map) {
            hash += 7 * key.hashCode()
            hash += 7 * value.sorted().hashCode()
        }
        return hash
    }
}
