package blog.in.action.pattern.builder;

public class EffectiveJavaBuilderPatttern {
	public static void main(String[] args) {
		NutritionFacts cocaCola = new NutritionFacts
				.Builder(240, 8) // 필수값 입력
				.calories(100)
				.sodium(35)
				.carbohydrate(27)
				.build();
	}
}

// Effective Java의 Builder Pattern
class NutritionFacts {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;

	public static class Builder {

		// Required parameters(필수 인자)
		private final int servingSize;
		private final int servings;

		// Optional parameters - initialized to default values(선택적 인자는 기본값으로 초기화)
		private int calories = 0;
		private int fat = 0;
		private int carbohydrate = 0;
		private int sodium = 0;

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}

		public Builder calories(int val) {
			calories = val;
			return this; // 이렇게 하면 . 으로 체인을 이어갈 수 있다.
		}

		public Builder fat(int val) {
			fat = val;
			return this;
		}

		public Builder carbohydrate(int val) {
			carbohydrate = val;
			return this;
		}

		public Builder sodium(int val) {
			sodium = val;
			return this;
		}

		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}

	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}

	public int getServingSize() {
		return servingSize;
	}

	public int getServings() {
		return servings;
	}

	public int getCalories() {
		return calories;
	}

	public int getFat() {
		return fat;
	}

	public int getSodium() {
		return sodium;
	}

	public int getCarbohydrate() {
		return carbohydrate;
	}
}
