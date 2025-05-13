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
    // VISA
    // Тест 1: VISA, в пределах лимитов
    val commission1 =
        calculateCommission(
            cardType = VISA,
            amountPerMonth = 0.0,
            amountPerDay = 0.0,
            amountTransfer = 10_000.0
        )
    println("Тест 1 (VISA, в пределах лимитов): Комиссия от суммы 10 000.0 руб. составит $commission1 руб.\n")

    // Тест 2: VISA, превышение дневного лимита
    val commission2 = calculateCommission(
        cardType = VISA,
        amountPerMonth = 200_000.0,
        amountPerDay = 145_000.0,
        amountTransfer = 10_000.0
    )
    println(message = "Тест 2 (VISA, превышение дневного лимита): Комиссия от суммы 10 000.0 руб. составит $commission2 руб.\n")

    // Тест 3: VISA, превышение месячного лимита
    val commission3 = calculateCommission(
        cardType = VISA,
        amountPerMonth = 599_000.0,
        amountPerDay = 36_000.0,
        amountTransfer = 10_000.0
    )
    println("Тест 3 (VISA, превышение месячного лимита): Комиссия от суммы 10 000.0 руб. составит $commission3 руб.\n")

    // MASTERCARD
    // Тест 1: MASTERCARD, в пределах лимитов
    val commission4 = calculateCommission(
        cardType = MASTERCARD,
        amountPerMonth = 40_000.0,
        amountPerDay = 5_000.0,
        amountTransfer = 20_000.0
    )
    println("Тест 1 (MASTERCARD, в пределах всех лимитов): Комиссия от суммы 20 000.0 руб. составит $commission4 руб.\n")

    // Тест 2: MASTERCARD, в пределах лимитов, но с превышением льготной суммы
    val commission5 =
        calculateCommission(
            cardType = MASTERCARD,
            amountPerMonth = 0.0,
            amountPerDay = 0.0,
            amountTransfer = 85_000.0
        )
    println("Тест 2 (MASTERCARD, в пределах лимитов, но с превышением льготной суммы): Комиссия от суммы 85 000.0 руб. составит $commission5 руб.\n")

    // Тест 3: MASTERCARD, в пределах лимитов, но с превышением льготной суммы
    val commission6 = calculateCommission(
        cardType = MASTERCARD,
        amountPerMonth = 40_000.0,
        amountPerDay = 8_000.0,
        amountTransfer = 60_000.0
    )
    println("Тест 3 (MASTERCARD, в пределах лимитов, но с превышением льготной суммы): Комиссия от суммы 60 000.0 руб. составит $commission6 руб.\n")

    // Тест 4: MASTERCARD, превышение месячного лимита
    val commission7 = calculateCommission(
        cardType = MASTERCARD,
        amountPerMonth = 600_000.0,
        amountPerDay = 8_000.0,
        amountTransfer = 20_000.0
    )
    println("Тест 4 (MASTERCARD, превышение месячного лимита): Комиссия от суммы 20 000.0 руб. составит $commission7 руб.\n")

    // Тест 5: MASTERCARD, превышение дневного лимита
    val commission8 = calculateCommission(
        cardType = MASTERCARD,
        amountPerMonth = 300_000.0,
        amountPerDay = 150_000.0,
        amountTransfer = 20_000.0
    )
    println("Тест 5 (MASTERCARD, превышение месячного лимита): Комиссия от суммы 20 000.0 руб. составит $commission8 руб.\n")

    // MIR
    // Тест 1: MIR, в пределах лимитов
    val commission9 = calculateCommission(
        cardType = MIR,
        amountPerMonth = 40_000.0,
        amountPerDay = 8_000.0,
        amountTransfer = 5_000.0
    )
    println("Тест 1 (MIR, в пределах лимитов): Комиссия от суммы 5 000.0 руб. составит $commission9 руб.\n")

    // НЕИЗВЕСТНАЯ КАРТА
    // Тест 1: Неизвестный тип карты
    val commission10 = calculateCommission(
        cardType = 99,
        amountPerMonth = 40_000.0,
        amountPerDay = 8_000.0,
        amountTransfer = 10_000.0
    )
    println("Тест 1 (неизвестный тип карты): Комиссия от суммы 10 000.0 руб. составит $commission10 руб.\n")
}


// вычисление комиссии
fun calculateCommission(
    cardType: Int = MIR,
    amountPerMonth: Double = MAX_MONTHLY_LIMIT,
    amountPerDay: Double = MAX_DAILY_LIMIT,
    amountTransfer: Double
): Double {
    // Сначала проверяем, не превысили ли мы лимиты. Если превысили, то комиссии не будет.
    // Во время проверки функция isWithinLimits выведет соответствующие сообщения в консоль.
    if (!isWithinLimits(amountPerDay, amountPerMonth, amountTransfer)) {
        return 0.0
    }

    return when (cardType) {
        VISA -> calculateVisaCommission(amountTransfer)
        MASTERCARD -> calculateMastercardCommission(amountPerMonth, amountTransfer)
        MIR -> 0.0
        else -> {
            println("Неизвестный тип карты.")
            0.0
        }
    }
}

// Функция проверяет лимиты и выводит соответствующие сообщения.
// Если какой-то из лимитов превышен - вернёт false, если перевод укладывается в лимиты - true
fun isWithinLimits(amountPerDay: Double, amountPerMonth: Double, amountTransfer: Double): Boolean {
    return when {
        amountPerDay + amountTransfer > MAX_DAILY_LIMIT -> {
            println("Операция отменена. Нельзя перевести более $MAX_DAILY_LIMIT рублей в сутки. Попробуйте в другой день.")
            false
        }

        amountPerMonth + amountTransfer > MAX_MONTHLY_LIMIT -> {
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
fun calculateMastercardCommission(
    amountPerMonth: Double,
    amountTransfer: Double
): Double {
    return when {
        amountPerMonth > MASTERCARD_MONTHLY_LIMIT -> {
            // комиссия взимается только с amountTransfer
            amountTransfer * MASTERCARD_COMMISSION_RATE + MASTERCARD_FIXED_FEE;
        }

        amountPerMonth + amountTransfer <= MASTERCARD_MONTHLY_LIMIT -> {
            // комиссия не взимается
            0.0;
        }

        else -> {
            //комиссия взимается с суммы превышения (amountTransfer + amountPerMonth - comissionFreeLimit)
            (amountTransfer + amountPerMonth - MASTERCARD_MONTHLY_LIMIT) * MASTERCARD_COMMISSION_RATE + MASTERCARD_FIXED_FEE
        }
    }
}