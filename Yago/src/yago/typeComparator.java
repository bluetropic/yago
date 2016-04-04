package yago;

import java.util.Comparator;

public class typeComparator implements Comparator<Type> {
	public int compare(Type t1, Type t2) {
		int cat1=t1.getCat(), cat2=t2.getCat();
		String typeId1=t1.getTypeId(), typeId2=t2.getTypeId();
		int result;
		if(cat1==cat2)  result=typeId1.compareTo(typeId2);
		else  result=cat1-cat2;
		return result;
	}
}
