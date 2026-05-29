package com.group3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerciseCategory {
	private int catID;
	private String catName;
	private List<TrackingType> allowedTrackingTypes;
	private List<ExerciseCategory> subCat;
	private transient ExerciseCategory parentCat;

	public ExerciseCategory(int catID, String catName, List<TrackingType> allowedTrackingTypes) {
		this.catID = catID;
		this.catName = catName;
		this.allowedTrackingTypes = allowedTrackingTypes;
		this.subCat = new ArrayList<>();
	}

	public void addSubCat(ExerciseCategory newSubCat) {
		if (this.subCat == null) {
			this.subCat = new ArrayList<>();
		}
		newSubCat.setParentCat(this);
		this.subCat.add(newSubCat);
	}

	public List<TrackingType> getAllowedTrackingType() {
		return allowedTrackingTypes;
	}

	public int getCatID() {
		return catID;
	}

	public String getCatName() {
		return catName;
	}

	public List<ExerciseCategory> getSubCat() {
		return subCat;
	}

	public void setParentCat(ExerciseCategory parentCat) {
		this.parentCat = parentCat;
	}

	@Override
	public String toString() {
		return catName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ExerciseCategory other = (ExerciseCategory) obj;
		return catID == other.catID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(catID);
	}
}