package com.group3.model;

import java.time.LocalDateTime;

public class NutritionLog {
	private final int userID;
	private final int logID;
	private final int productID;
	private final String productName;
	private final LocalDateTime addTime;
	private final Integer quantity;
	private final Double energy;
	private final Double protein;
	private final Double fat;
	private final Double carbohydrates;

	private NutritionLog(Builder builder) {
		this.userID = builder.userID;
		this.logID = builder.logID;
		this.productID = builder.productID;
		this.productName = builder.productName;
		this.addTime = builder.addTime;
		this.quantity = builder.quantity;
		this.energy = builder.energy;
		this.protein = builder.protein;
		this.fat = builder.fat;
		this.carbohydrates = builder.carbohydrates;
	}
	
	public int getUserID() {
		return userID;
	}

	public int getLogID() {
		return logID;
	}
	
	public int getProductID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}

	public LocalDateTime getAddTime() {
		return addTime;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Double getEnergy() {
		return energy;
	}

	public Double getProtein() {
		return protein;
	}

	public Double getFat() {
		return fat;
	}

	public Double getCarbohydrates() {
		return carbohydrates;
	}

	public static class Builder {
		private int userID;
		private int logID = -1;
		private int productID = -1;
		private String productName;
		private LocalDateTime addTime = LocalDateTime.now();
		private int quantity = 1;
		private Double energy = null;
		private Double protein = null;
		private Double fat = null;
		private Double carbohydrates = null;
		
		public Builder setUserID(int userID) {
			this.userID = userID;
			return this;
		}
		
		public Builder setlogID(int logID) {
			this.logID = logID;
			return this;
		}
		
		public Builder setProductID(int productID) {
			this.productID = productID;
			return this;
		}

		public Builder setProductName(String productName) {
			this.productName = productName;
			return this;
		}

		public Builder setAddTime(LocalDateTime addTime) {
			this.addTime = addTime;
			return this;
		}

		public Builder setQuantity(int quantity) {
			this.quantity = quantity;
			return this;
		}

		public Builder setEnergy(Double energy) {
			this.energy = energy;
			return this;
		}

		public Builder setProtein(Double protein) {
			this.protein = protein;
			return this;
		}

		public Builder setFat(Double fat) {
			this.fat = fat;
			return this;
		}

		public Builder setCarbohydrates(Double carbohydrates) {
			this.carbohydrates = carbohydrates;
			return this;
		}

		public NutritionLog build() {
			if (productID == -1 || productName == null || productName.isBlank()) {
				throw new IllegalStateException("Cần có ID và Tên sản phẩm!");
			}
			return new NutritionLog(this);
		}
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Sản phẩm: ").append(productName).append(" (ID: ").append(productID).append(")\n");
        sb.append("Số lượng: ").append(quantity).append("\n");
        sb.append("Thông tin dinh dưỡng:\n");
        
        if (energy != null) sb.append("Calo: ").append(energy).append(" kcal\n");
        if (protein != null) sb.append("Protein: ").append(protein).append("g\n");
        if (fat != null) sb.append("Fat: ").append(fat).append("g\n");
        if (carbohydrates != null) sb.append("Carb: ").append(carbohydrates).append("g\n");
        
        return sb.toString();
    }
}
