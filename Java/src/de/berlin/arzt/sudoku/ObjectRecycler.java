package de.berlin.arzt.sudoku;
import java.util.ArrayList;
import java.util.List;


public class ObjectRecycler <T> {
	private List<T> recycleables;
	
	public ObjectRecycler(int capacity) {
		recycleables = new ArrayList<T>(capacity);		
	}
	
	public boolean hasRecycleables() {
		return recycleables.size() > 0;
	}
	
	public T recycle() {
		return recycleables.remove(recycleables.size()-1);
	}
	
	public void add(T item) {
		recycleables.add(item);
	}
}
