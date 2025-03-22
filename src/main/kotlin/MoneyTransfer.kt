fun main() {
    //Проверяем, как вычисляется комиссия
    var amount: Double = 182_203.28;
    println("Комиссия от платежа "+amount+" руб. составит "+calculateCommission(amount)+" руб.");
}

/**
 * Принимает сумму платежа
 * Возвращает комиссию в рублях
 */
fun calculateCommission(amount: Double): Double {
    val preCalculation: Double = amount / 100 * 0.75
    var commission: Double = if (preCalculation > 35) preCalculation else 35.0;
    return commission
}