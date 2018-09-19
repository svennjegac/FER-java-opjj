package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import org.junit.Test;

public class MinimizerParserTest {

	@Test
	public void simpleInput() {
		MinimizerParser.parseUserInput("f(A)=true");
	}
	
	@Test
	public void simpleInput2() {
		MinimizerParser.parseUserInput("Ta_g2_(A,B   ,   C)   =    1");
	}
	
	@Test
	public void simpleInput3() {
		MinimizerParser.parseUserInput("g2   ( A , B  , C)   =    A|[  0   ]");
	}

	@Test
	public void simpleInput4() {
		MinimizerParser.parseUserInput("g2   ( A , B  , C)   =    A|[0, 3]");
	}
	
	@Test
	public void simpleInput5() {
		MinimizerParser.parseUserInput("g2   ( A , B  , C)=A | [ 0 , 3]");
	}
	
	@Test
	public void simpleInput6() {
		MinimizerParser.parseUserInput("g2   ( A )= [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongName() {
		MinimizerParser.parseUserInput("ae[( A )= [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongName2() {
		MinimizerParser.parseUserInput("2e( A )= [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongParentheses() {
		MinimizerParser.parseUserInput("e( A ()= [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongParentheses2() {
		MinimizerParser.parseUserInput("e(( A )= [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongParentheses3() {
		MinimizerParser.parseUserInput("e( A ))= [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongParentheses4() {
		MinimizerParser.parseUserInput("(e( A )= [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongEqualsSign() {
		MinimizerParser.parseUserInput("e( A )== [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongEqualsSign2() {
		MinimizerParser.parseUserInput("e( A )= [   ] | [ 0 , 3]=");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongEqualsSign3() {
		MinimizerParser.parseUserInput("e( A ) [   ] | [ 0 , 3]=");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongEqualsSign4() {
		MinimizerParser.parseUserInput("e( A ) [   ] | [ 0 , 3]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidExpression() {
		MinimizerParser.parseUserInput("e( A )= [ ,  ] | []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidExpression2() {
		MinimizerParser.parseUserInput("e( A )= [1 ,  ] | []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidExpression3() {
		MinimizerParser.parseUserInput("e( A )= [ , 2 ] | []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidExpression4() {
		MinimizerParser.parseUserInput("e( A )= [1 , 2 ,] | []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidExpression5() {
		MinimizerParser.parseUserInput("e( A )= [1 ,, 2 ] | []");
	}
	
	@Test(expected=IllegalStateException.class)
	public void unknownVariable() {
		MinimizerParser.parseUserInput("e( A )= A or B | []");
	}
	
	@Test(expected=IllegalStateException.class)
	public void unknownVariable2() {
		MinimizerParser.parseUserInput("e( A )= b | []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void unknownVariable3() {
		MinimizerParser.parseUserInput("e()= b | []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void unknownVariable4() {
		MinimizerParser.parseUserInput("e( a )= a | []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void expressionDelimiter() {
		MinimizerParser.parseUserInput("e( A )= a || []");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void expressionDelimiter2() {
		MinimizerParser.parseUserInput("e( A, B )= a | [1] | [0]");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void expressionDelimiter3() {
		MinimizerParser.parseUserInput("e( A, B )= [1] |");
	}
}
