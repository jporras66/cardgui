package com.indarsoft.cardutl.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.indarsoft.cardutl.file.CardFile;
import com.indarsoft.cryptocard.card.Card;
import com.indarsoft.utl.Utl;

public class CardFileTest {
	
	private static final String separator = File.separator ;
	private static final String testfilename  = "cards.txt";
	
	@Test
	public void testReadArrayListRecord() {
		
		String fileName =  Utl.getPwd() + separator + "data" + separator + testfilename ;
		CardFile u = new CardFile( fileName ) ; 
		
		try{
			ArrayList<String> als = u.readArrayListRecord ( ) ;
			for (int i=0; i< als.size(); i++){
				System.out.println("testReadArrayListRecord : " + als.get(i) );
			}
		}
		catch( IOException e){
			System.out.println (  e.getMessage() ) ;
			System.out.println("testReadArrayListRecord : KO !!") ;
			assertFalse( true) ;
		}
		
		System.out.println("testReadArrayListRecord : OK !!") ;
		assertTrue( true) ;	
		
	}

	@Test
	public void testReadArrayListCard() {

		String fileName =  Utl.getPwd() + separator + "data" + separator + testfilename ;
		CardFile u = new CardFile( fileName ) ; 
		
		try{

			ArrayList<Card> alc = u.readArrayListCard ( ) ;
			for (int i=0; i< alc.size(); i++){
				System.out.println("testReadArrayListCard : " + alc.get(i).getPanNumber() + "-" + alc.get(i).getPvv()  + "-" + alc.get(i).getOffset());
			}
		}
		catch( IOException e){
			System.out.println (  e.getMessage() ) ;
			System.out.println("testReadArrayListCard : KO !!"  );
			assertFalse( true) ;
		}
		System.out.println("testReadArrayListCard : OK !!"  );
		assertTrue( true) ;	
	}
}
