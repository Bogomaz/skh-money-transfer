Программа вычисляет комиссию в размере 0.75% от размера денежного перевода. 
В переменной amount хранится сумма перевода в рублях.
Программа выводит в консоль размер комиссии в зависимости от суммы перевода (также в рублях).

=====
Добавлены новые функции.
1. Теперь сумма комиссии зависит ещё и от типа карты, с которой мы переводим средства.
   1.1. За переводы с карты Mastercard комиссия не взимается, пока не превышен месячный лимит в 75 000 руб. Если лимит превышен, комиссия составит 0,6% + 20 руб.
   1.2. За переводы с карты Visa комиссия составит 0,75%, минимальная сумма комиссии 35 руб.
   1.3. За переводы с карты Мир комиссия не взимается.

2. Введены лимиты на суммы перевода за сутки и за месяц. Максимальная сумма перевода с одной карты:
   150 000 руб. в сутки
   600 000 руб. в месяц
   Комиссия в лимитах не учитывается.

Т. е. если пользователь решит перевести матери 150 000 руб. с карты Mastercard впервые за месяц, то его мать получит всю сумму, а комиссия будет удержана сверх этого. Сумма комиссии составит 75 000 * 0,006 + 20 = 470 руб. (т. к. с первых 75 000 руб. комиссия не взимается).

Функция, вычисляющая комиссию, принимает:

1. тип карты (по умолчанию Мир);
2. сумму предыдущих переводов в этом месяце (по умолчанию 0 рублей);
3. сумму совершаемого перевода.

В случае превышения какого-либо из лимитов операция блокируется.
