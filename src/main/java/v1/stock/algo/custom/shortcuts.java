package v1.stock.algo.custom;

import v1.stock.test.bar.barTest;

public class shortcuts extends barTest {
    public static double hl2(double high, double low){
        return ((high + low)/2);
    }

    public static double hlc3(double high, double low, double close){
        return ((high + low + close)/3);
    }

    public static double avg(double[] x) {
    	double y = 0;
    	for(double d : x){
    		y += d;
		}

    	y = y / x.length;

    	return y;
	}

    public static float[] dtf(double[] x){
    	float[] y = new float[x.length];

    	for (int i = 0; i < x.length; i++){
    		y[i] = Float.parseFloat(String.valueOf(x[i]));
		}

    	return y;
	}

    public static double min(double values[]){
        double min = 0;
        for (double d : values) {
            if(d < min)
                min = d;
        }

        return min;
    }

    public static double max(double values[]){
        double max = 0;
        for(double d : values){
            if(d > max){
                max = d;
            }
        }

        return max;
    }

    public static double abs(double value){
        return Math.abs(value);
    }

    public static double[] abs(double[] values){
        double[] finalVals = new double[values.length];
        int counter = 0;

        for(double d : values){
            finalVals[counter] = Math.abs(d);
            counter++;
        }

        return finalVals;
    }

    public static double getClose(int x){
    	if(x > 100)
    		return 0;
    	return bars.get(x).getClose();
	}

    public static double[] getCloses(int num){
        double[] finalVals = new double[num];
        int counter = 0;

        if(num > 100)
            return null;

        for (int i = 99 - (num - 1); i <= 99; i++){
            finalVals[counter] = bars.get(i).getClose();
            counter++;
        }

        return finalVals;
    }

    public static double[] getOpens(int num){
        double[] finalVals = new double[num];
        int counter = 0;

        if(num > 100)
            return null;

        for (int i = 99 - (num - 1); i <= 99; i++){
            finalVals[counter] = bars.get(i).getOpen();
            counter++;
        }

        return finalVals;
    }

    public static double[] getHighs(int num){
        double[] finalVals = new double[num];
        int counter = 0;

        if(num > 100)
            return null;

        for (int i = 99 - (num - 1); i <= 99; i++){
            finalVals[counter] = bars.get(i).getHigh();
            counter++;
        }

        return finalVals;
    }

    public static double[] getLows(int num){
        double[] finalVals = new double[num];
        int counter = 0;

        if(num > 100)
            return null;

        for (int i = 99 - (num - 1); i <= 99; i++){
            finalVals[counter] = bars.get(i).getLow();
            counter++;
        }

        return finalVals;
    }

    public static boolean na(double num){
        return Double.isNaN(num);
    }

    public static double nz(double num, double replace){
        if(na(num))
            return replace;

        return num;
    }

    public static double nz(double num){
    	if(na(num))
    		return 0;

    	return num;
	}

    //src is the adjusted number
	//order of arrays, [1] oldest, [99] newest

    //pine_atr(length) =>
    //    trueRange = na(high[1])? high-low : max(max(high - low, abs(high - close[1])), abs(low - close[1]))
    //    //true range can be also calculated with tr(true)
    //    rma(trueRange, length)

    //pine_rma(src, length) =>
    //    alpha = length
    //    sum = 0.0
    //    sum := na(sum[1]) ? sma(src, length) : (src + (alpha - 1) * nz(sum[1])) / alpha

    //pine_sma(x, y) =>
    //    sum = 0.0
    //    for i = 0 to y - 1
    //        sum := sum + x[i] / y
    //    sum

	public static double[] sma(int length){
		//max len should be 15
		int barcountR = getBarCount - length;
		double[] sma = new double[barcountR - (length - 1)];
		int temp = 0;

		for(int i = getBarCount - barcountR; i <= barcountR; i++){
			double[] k = new double[length];
			double m = 0;

			for(int j = i; j <= i + length - 1; j++){
				k[j-i] = bars.get(j).getClose();
			}
			for(int j = 0; j <= length - 1; j++){
				m += k[j];
			}

			double l = m/k.length;

			sma[temp] = l;
			temp++;
		}

		return sma;
	}
//
//	public static double[] ema(int length){
//
//
//	}

    public static double[] tr(int length) {
		int barcountR = getBarCount - length;
		double[] tr = new double[barcountR - (length - 1)];
		int counter = 0;

		//trueRange = na(high[1])? high-low : max(max(high - low, abs(high - close[1])), abs(low - close[1]))
		for(int i = getBarCount - barcountR; i <= barcountR; i++){
			double temp;

			if(na(bars.get(i-1).getHigh())){
				temp = bars.get(i).getHigh() - bars.get(i).getLow();
			} else {
				double[] val1 = new double[2];
				double[] val2 = new double[2];
				val1[0] = bars.get(i).getHigh() - bars.get(i).getLow();
				val1[1] = abs(bars.get(i).getHigh() - bars.get(i - 1).getClose());
				val2[0] = max(val1);
				val2[1] = abs(bars.get(i).getLow() - bars.get(i - 1).getClose());
				temp = max(val2);
			}

			tr[counter] = temp;
			counter++;
		}

		return tr;
    }

	public static double[] wld(int length){
		double[] wld = new double[100];
		int counter = 0;

		for(int i = 0; i <= 99; i++){
			if(counter == 0){
				wld[counter] = getClose(i);
			} else {
				wld[counter] = wld[counter - 1] + (getClose(i) - wld[counter - 1])/length;
			}
			counter++;
		}
		wld[0] = wld[1];

		return wld;
	}

    public static double[] wldMA(int length){
    	//wild=nz(wild[1])+(close-nz(wild[1]))/malength
		//col1= wild>wild[1]
		//col3= wild<wild[1]
		//color = col1 ? green : col3 ? red : yellow

		//TODO: you made it into a moving average dummy

		int barcountR = getBarCount - length;
		double[] wld = new double[barcountR];
		int counter = 0;
		boolean firstLoop = true;

		for(int i = (getBarCount - barcountR) + 1 - (length - 1); i <= barcountR; i++){
			double tempa = getClose(i);
			double[] avg = new double[length];
			int counterI = 0;

			if(firstLoop){
				avg[0] = tempa;
				counter++;
				counterI++;
				for(int j = i + 1; j < i + length; j++){
					double temp = wld[counter - 1] + (getClose(j - 1) - wld[counter - 1]);
					avg[counterI] = temp;
					counterI++;
				}

				firstLoop = false;
			} else {
				for (int j = i; j < i + length; j++){
					double temp = wld[counter - 1] + (getClose(j - 1) - wld[counter - 1]);
					avg[counterI] = temp;
					counterI++;
				}
			}

			wld[counter] = shortcuts.avg(avg);

			counter++;
		}

		wld[0] = wld[1];

		return wld;
	}

	public static double[] atr(int length){
		double[] tr = tr(length);

		return shortcutsWithData.wld(tr, length);
	}
}
