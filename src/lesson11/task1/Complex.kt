@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import lesson1.task1.sqr


/**
 * Фабричный метод для создания комплексного числа из строки вида x+yi
 */
fun Complex(s: String): Complex {
    val parts = s.split("""[+-]""".toRegex())
    when (parts.size) {
        2 ->
            return if (s.contains("-")) {
                val re = parts[0].toDouble()
                val im = -parts[1].dropLast(1).toDouble()
                Complex(re, im)
            } else {
                val re = parts[0].toDouble()
                val im = parts[1].dropLast(1).toDouble()
                Complex(re, im)
            }
        3 -> return if (s.count { it == '-' } == 2) {
            val re = -parts[1].toDouble()
            val im = -parts[2].dropLast(1).toDouble()
            Complex(re, im)
        } else {
            val re = -parts[1].toDouble()
            val im = parts[2].dropLast(1).toDouble()
            Complex(re, im)
        }
    }
    return Complex(1.0, 1.0)
}

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(this.re + other.re, this.im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(re * -1.0, im * -1.0)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex =
        Complex(re * other.re - im * other.im, im * other.re + re * other.im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex =
        Complex(
            (re * other.re + im * other.im) / (sqr(other.re) + sqr(other.im)),
            (im * other.re - re * other.im) / (sqr(other.re) + sqr(other.im))
        )

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Complex && re.equals(other.re) && im.equals(other.im)

    /**
     * Преобразование в строку
     */
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(re)
        if (im > 0.0) sb.append("+")
        sb.append(im)
        sb.append("i")
        return "$sb"
    }
}
