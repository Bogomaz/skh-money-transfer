const val VISA = 0;
const val MASTERCARD = 1;
const val MIR = 3;

const val MAX_DAILY_LIMIT = 150_000.0
const val MAX_MONTHLY_LIMIT = 600_000.0
const val MASTERCARD_MONTHLY_LIMIT = 75_000.0

const val VISA_COMMISSION_RATE = 0.0075
const val MASTERCARD_COMMISSION_RATE = 0.006

const val MASTERCARD_FIXED_FEE = 20.0
const val MIN_VISA_COMMISSION = 35.0

fun main() {
    // Тест 1: VISA, в пределах лимитов
    val commission1 = calculateCommission(cardType = VISA, amountTransfer = 10_000.0)
    println("Тест 1 (VISA, в пределах лимитов): Комиссия от суммы 10 000.0 руб. составит $commission1 руб.\n")

    // Тест 2: MASTERCARD, в пределах лимитов
    val commission2 = calculateCommission(cardType = MASTERCARD, amountTransfer = 20_000.0)
    println("Тест 2 (MASTERCARD, в пределах лимитов): Комиссия от суммы 20 000.0 руб. составит $commission2 руб.\n")

    // Тест 3: MIR, в пределах лимитов
    val commission3 = calculateCommission(cardType = MIR, amountTransfer = 5_000.0)
    println("Тест 3 (MIR, в пределах лимитов): Комиссия от суммы 5 000.0 руб. составит $commission3 руб.\n")

    // Тест 4: VISA, превышение дневного лимита
    val commission4 = calculateCommission(cardType = VISA, amountTransfer = 10_000.0)
    println("Тест 4 (VISA, превышение дневного лимита): Комиссия от суммы 10 000.0 руб. составит $commission4 руб.\n")

    // Тест 5: MASTERCARD, превышение месячного лимита
    val commission5 = calculateCommission(cardType = MASTERCARD, amountTransfer = 20_000.0)
    println("Тест 5 (MASTERCARD, превышение месячного лимита): Комиссия от суммы 20 000.0 руб. составит $commission5 руб.\n")

    // Тест 6: VISA, превышение месячного лимита
    val commission6 = calculateCommission(cardType = VISA, amountTransfer = 10_000.0)
    println("Тест 6 (VISA, превышение месячного лимита): Комиссия от суммы 10 000.0 руб. составит $commission6 руб.\n")

    // Тест 7: Неизвестный тип карты
    val commission7 = calculateCommission(cardType = 99, amountTransfer = 10_000.0)
    println("Тест 7 (неизвестный тип карты): Комиссия от суммы 10 000.0 руб. составит $commission7 руб.\n")

    // Тест 8: MASTERCARD, в пределах лимитов, но с превышением льготной суммы
    val commission8 = calculateCommission(cardType = MASTERCARD, amountTransfer = 150_000.0)
    println("Тест 8 (MASTERCARD, в пределах лимитов, но с превышением льготной суммы): Комиссия от суммы 150 000.0 руб. составит $commission8 руб.\n")

}


// вычисление комиссии
fun calculateCommission(
    cardType: Int = MIR,
    amountPerMonth: Double = MAX_MONTHLY_LIMIT,
    amountPerDay: Double = MAX_DAILY_LIMIT,
    amountTransfer: Double): Double {
    // сначала проверяем, не превысили ли мы лимиты. Если превысили, то комиссии не будет.
    // во время проверки функция isWithinLimits выведет соответствующие сообщения в консоль.
    if (!isWithinLimits(amountPerDay, amountPerMonth)) {
        return 0.0
    }

    return when (cardType) {
        VISA -> calculateVisaCommission(amountTransfer)
        MASTERCARD -> calculateMastercardCommission(MASTERCARD_MONTHLY_LIMIT, amountTransfer)
        MIR -> 0.0
        else -> {
            println("Неизвестный тип карты.")
            0.0
        }
    }
}

// Функция проверяет лимиты и выводит соответствующие сообщения.
fun isWithinLimits(amountPerDay: Double, amountPerMonth: Double): Boolean {
    return when {
        amountPerDay > MAX_DAILY_LIMIT -> {
            println("Операция отменена. Нельзя перевести более $MAX_DAILY_LIMIT рублей в сутки. Попробуйте в другой день.")
            false
        }

        amountPerMonth > MAX_MONTHLY_LIMIT -> {
            println("Операция отменена. Нельзя перевести более $MAX_MONTHLY_LIMIT рублей в месяц. Попробуйте в следующем месяце.")
            false
        }

        else -> true
    }
}

// Функция вычисляет комиссию для карты visa
fun calculateVisaCommission(amountTransfer: Double): Double {
    val preCalculation = amountTransfer * VISA_COMMISSION_RATE
    return if (preCalculation > MIN_VISA_COMMISSION) preCalculation else MIN_VISA_COMMISSION
}

// Функция вычисляет комиссию для карты mastercard
fun calculateMastercardCommission(comissionFreeLimit: Double, amountTransfer: Double): Double {
    return if (amountTransfer - comissionFreeLimit > 0)
        (amountTransfer - comissionFreeLimit) * MASTERCARD_COMMISSION_RATE + MASTERCARD_FIXED_FEE
        else 0.0
}