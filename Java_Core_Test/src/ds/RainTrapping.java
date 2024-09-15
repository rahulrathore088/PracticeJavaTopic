package ds;

import java.util.Arrays;

public class RainTrapping {

	public static void main(String[] args) {
		int[] tow = new int[] {4,2,0,3,2,5};//{1, 0, 2, 1, 0, 1, 3, 0, 0, 0, 3, 2, 1, 0, 1 };// 
		int len = tow.length;

		int[][] dp = new int[len][len];

		int sum = trap2(tow);
		System.out.println(sum);

	}

	public static int trap(int[] height) {
		int count = 0;
		for (int i = 0; i < height.length - 1; i++) {
			int th = height[i];
			int nh = height[i + 1];
			if (nh >= th) {
				continue;
			}
			int lastcount = count;

			int maxEl = 0;
			for (int j = i + 1; j < height.length; j++) {
				int bucketH = height[j];
				if (bucketH < th) {
					maxEl = Math.max(maxEl, bucketH);
					continue;
				}
				for (int n = i + 1; n < j; n++) { // { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
					int dif = th - height[n];
					count += dif;
				}
				if (count != lastcount)
					i = j - 1;
				break;
			}
			if (count == lastcount) {
				for (int j = i + 1; j < height.length; j++) {
					int bucketH = height[j];
					if (bucketH < maxEl) {
						continue;
					}
					for (int n = i + 1; n < j; n++) {
						int dif = maxEl - height[n];
						count += dif;
					}
					if (count != lastcount)
						i = j - 1;
					break;
				}
			}
		}
		return count;
	}

	public static int trap2(int[] height) {
		int mh = Arrays.stream(height).max().getAsInt();
		int block_vol = Arrays.stream(height).reduce(0, Integer::sum);
		int left = 0;
		int max_left = 0;

		while (height[left] < mh) {
			if (height[left] > max_left) {
				max_left = height[left];
			}
			height[left] = max_left;
			left += 1;
		}

		int right = height.length - 1;
		int max_right = 0;

		while (height[right] < mh) {
			if (height[right] > max_right) {
				max_right = height[right];
			}

			height[right] = max_right;
			right -= 1;
		}
		int left_sum = 0;
		int right_sum = 0;
		for (int i = 0; i < left; i++) {
			left_sum += height[i];
		}

		for (int i = right; i < height.length; i++) {
			right_sum += height[i];
		}

		int filled_vol = left_sum + right_sum + (right - left) * mh;
		return filled_vol - block_vol;
	}

}
