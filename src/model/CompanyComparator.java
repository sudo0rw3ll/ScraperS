package model;

import java.util.Comparator;

public class CompanyComparator implements Comparator<Company>{

	@Override
	public int compare(Company o1, Company o2) {
		return o1.getNaziv().compareTo(o2.getNaziv());
	}

}
