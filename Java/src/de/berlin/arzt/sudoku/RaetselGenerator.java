package de.berlin.arzt.sudoku;
/**
  *
  * Beschreibung.
  *
  * @version 1.0 vom 03.11.2005
  * @author
  */

import java.util.Random;

public class RaetselGenerator {
  private int[][] test;
  private boolean [][] gesucht;
  int countGesucht;
  Sudoku s;
  Random zufall = new Random();
  
  public RaetselGenerator(Sudoku s) {
    this.s = s;
  }

  public RaetselGenerator() {
  }
  
  public Sudoku erstelleRaetsel(Sudoku in, long id) {
    zufall = new Random(id);
    return erstelleRaetsel(in);
  }
  
  public Sudoku erstelleRaetsel(Sudoku in) {
    s = in.copy();
    gesucht = new boolean[s.getTiefe()][s.getTiefe()];
    countGesucht = 0;
    int x,y;
    while (countGesucht<s.getTiefe()*s.getTiefe()) {
      do {
        x = zufall.nextInt(s.getTiefe());
        y = zufall.nextInt(s.getTiefe());
      }
      while (gesucht[x][y]);
      gesucht[x][y] = true;
      countGesucht= countGesucht+1;
      if (entfernbar(x,y)) {
        s.set(x,y,0);
      }
    }
    return s;
  }
  
  public boolean entfernbar(int x, int y) {
    if (isHiddenSingle(x,y)) return true;
    int count = 0;
    boolean[] flags = new boolean[s.getTiefe()];
    int wert =0;
    int ix,iy;
    for (iy = 0;iy<s.getTiefe();iy++) {
      wert = s.get(x,iy);
      if (wert!=0) {
        if (!flags[wert-1]) {
          flags[wert-1]=true;
          count++;
        }
      }
      if (count == 9) return true;
    }
    for (ix = 0;ix<s.getTiefe();ix++) {
      wert = s.get(ix,y);
      if (wert!=0) {
        if (!flags[wert-1]) {
          flags[wert-1]=true;
          count++;
        }
      }
      if (count == 9) return true;
    }
    int ox=(x/s.getBreite())*s.getBreite();
    int oy=(y/s.getHoehe())*s.getHoehe();
    for (ix=0;ix<s.getBreite();ix++) {
      for (iy=0;iy<s.getHoehe();iy++) {
        wert = s.get(ix+ox,iy+oy);
        if (wert!=0) {
          if (!flags[wert-1]) {
            flags[wert-1]=true;
            count++;
          }
        }
        if (count == 9) return true;
      }
    }
    return false;
  }
  
  public boolean isHiddenSingle(int x, int y) {
    int wert;
    int ox=(x/s.getBreite())*s.getBreite();
    int oy=(y/s.getHoehe())*s.getHoehe();
    for (int ix=0;ix<s.getBreite();ix++) {
      for (int iy=0;iy<s.getHoehe();iy++) {
        wert = s.get(ix+ox,iy+oy);
        if (wert==0) {
          if (!stelleBelegt(ix+ox,iy+oy, s.get(x,y))) return false;
        }
      }
    }
    return true;
  }
  
  public boolean stelleBelegt(int x, int y, int wert) {
    int gx = (x/s.getBreite())*s.getBreite();
    int gy = (y/s.getHoehe())*s.getHoehe();
    int i;
    for (i=0;i<gx;i++) {
      if (s.get(i,y)==wert) return true;
    }
    for (i=gx+s.getBreite();i<s.getTiefe();i++) {
      if (s.get(i,y)==wert) return true;
    }
    for (i=0;i<gy;i++) {
      if (s.get(x,i)==wert) return true;
    }
    for (i=gy+s.getHoehe();i<s.getTiefe();i++) {
      if (s.get(x,i)==wert) return true;
    }
    return false;
  }
  
  public static void main(String[] args) {

  }
}

