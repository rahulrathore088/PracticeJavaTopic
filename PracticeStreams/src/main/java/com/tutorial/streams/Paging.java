package com.tutorial.streams;

import java.util.List;

public class Paging{
	public record Sesame(String name, boolean human) {
		@Override 
		public String toString() {
			return name();
		}
	}
	
	public record Page(List<Sesame> list, long count) {}
}
