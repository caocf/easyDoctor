package com.easyhoms.easydoctor.common.utils;


import com.easyhoms.easydoctor.common.bean.ContactLetter;

import java.util.Comparator;

/**
 *
 * 比较首字母排序
 */
public class PinyinComparator implements Comparator<ContactLetter> {

	public int compare(ContactLetter o1, ContactLetter o2) {
		if (o1.sortLetters.equals("@")
				|| o2.sortLetters.equals("#")) {
			return -1;
		} else if (o1.sortLetters.equals("#")
				|| o2.sortLetters.equals("@")) {
			return 1;
		} else {
			return o1.sortLetters.compareTo(o2.sortLetters);
		}
	}

}
