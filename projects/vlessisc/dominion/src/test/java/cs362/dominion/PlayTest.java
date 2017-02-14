package cs362.dominion;

import static org.junit.Assert.*;

import java.io.PrintStream;

import org.junit.Test;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import cs362.dominion.Play;
import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidSelectionException;


public class PlayTest {


	/**
	 * This is a high level game playing test. 
	 * It is impossible to test all possible configurations explicitly, but running a game
	 * which tests many configurations in random will help to discover errors which are not 
	 * feasible to test explicitly 
	 */
	@Test
	public void testPlay() {
		PrintStream originalStream = System.out;
		
		for(int i=0; i<100; i++){
			System.setOut(dummyStream);
			try {
				Play.main(null);
			} catch (InstantiationException e) {
				System.setOut(originalStream);
				fail("Triggered InstantiationException");
			} catch (IllegalAccessException e) {
				System.setOut(originalStream);
				fail("Triggered IllegalAccessException");
			} catch (InvalidSelectionException e) {
				System.setOut(originalStream);
				fail("Triggered InvalidSelectionException");
			} catch (ConfiguationException e) {
				System.setOut(originalStream);
				fail("Triggered ConfiguationException");
			}
			// Tests the testers default kingdom card initialization
			Play.randomizeKingdomCards=true;
		}
		
		System.setOut(originalStream);
	
		
	}
	
	/**
	 * Some of the methods under test are methods which print to the console. 
	 * In this case we a dummy print stream will be used to prevent the console being
	 * flooded with messages
	 */
	PrintStream dummyStream = new PrintStream(new OutputStream(){
	    public void write(int b) {
	        //NO-OP
	    }

		@Override
		public InputStream create_input_stream() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void write_boolean(boolean value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_char(char value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_wchar(char value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_octet(byte value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_short(short value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_ushort(short value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_long(int value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_ulong(int value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_longlong(long value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_ulonglong(long value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_float(float value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_double(double value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_string(String value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_wstring(String value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_boolean_array(boolean[] value, int offset,
				int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_char_array(char[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_wchar_array(char[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_octet_array(byte[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_short_array(short[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_ushort_array(short[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_long_array(int[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_ulong_array(int[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_longlong_array(long[] value, int offset,
				int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_ulonglong_array(long[] value, int offset,
				int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_float_array(float[] value, int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_double_array(double[] value, int offset,
				int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_Object(Object value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_TypeCode(TypeCode value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write_any(Any value) {
			// TODO Auto-generated method stub
			
		}
	});
	

}
