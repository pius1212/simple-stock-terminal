package stock.algo.custom;

public class shortcutsWithData {
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

	public static double[] sma(double[] data, int length){
		//max len should be 15
		int barcountR = data.length - length;
		double[] sma = new double[barcountR - (length - 1)];
		int temp = 0;

		for(int i = data.length - barcountR; i <= barcountR; i++){
			double[] k = new double[length];
			double m = 0;

			for(int j = i; j <= i + length - 1; j++){
				k[j-i] = data[j];
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

	public static double[] wld(double[] data, int length){
		double[] wld = new double[100];
		int counter = 0;

		for(int i = 0; i <= 99; i++){
			if(counter == 0){
				wld[counter] = data[i];
			} else {
				wld[counter] = wld[counter - 1] + (data[1] - wld[counter - 1])/length;
			}
			counter++;
		}
		wld[0] = wld[1];

		return wld;
	}

	public static double[] wldMA(double[] data, int length){
		//wild=nz(wild[1])+(close-nz(wild[1]))/malength
		//col1= wild>wild[1]
		//col3= wild<wild[1]
		//color = col1 ? green : col3 ? red : yellow

		//TODO: you made it into a moving average dummy

		int barcountR = data.length - length;
		double[] wld = new double[barcountR];
		int counter = 0;
		boolean firstLoop = true;

		for(int i = (data.length - barcountR) + 1 - (length - 1); i <= barcountR; i++){
			double tempa = data[i];
			double[] avg = new double[length];
			int counterI = 0;

			if(firstLoop){
				avg[0] = tempa;
				counter++;
				counterI++;
				for(int j = i + 1; j < i + length; j++){
					double temp = wld[counter - 1] + (data[j - 1] - wld[counter - 1]);
					avg[counterI] = temp;
					counterI++;
				}

				firstLoop = false;
			} else {
				for (int j = i; j < i + length; j++){
					double temp = wld[counter - 1] + (data[j - 1] - wld[counter - 1]);
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
}
