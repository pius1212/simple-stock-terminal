package v1.stock.test.indicator;

import v1.stock.test.bar.barTest;

public class supres extends barTest {

}
//showRS   = input(title="⤒⤓ Show Res-Sup", defval=true)
//lengthRS = input(title="⤒⤓ Res-Sup Length", defval=13, type=integer)
//highRS   = valuewhen(high >= highest(high, lengthRS), high, 0)
//lowRS    = valuewhen(low <= lowest(low, lengthRS), low, 0)
//plot(title="⤒ Resistance", series=showRS and highRS ? highRS : na, color=highRS != highRS[1] ? na : olive, linewidth=1, transp=25, offset=0)
//plot(title="⤓ Support", series=showRS and lowRS ? lowRS : na, color=lowRS != lowRS[1] ? na : maroon, linewidth=1, transp=25, offset=0)
