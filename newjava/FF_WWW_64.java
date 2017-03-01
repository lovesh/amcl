/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

/* Large Finite Field arithmetic */
/* AMCL mod p functions */

package amcl;

public final class FF_WWW {

/* RSA/DH modulus length as multiple of BIGBITS */
	public static final int FFLEN=@ML@;

/* Don't Modify from here... */


/* Finite field support - for RSA, DH etc. */
	public static final int FF_BITS=(BIG_XXX.BIGBITS*FFLEN); /* Finite Field Size in bits - must be 256.2^n */
	public static final int HFLEN=(FFLEN/2);  /* Useful for half-size RSA private key operations */

	public static final int P_MBITS=BIG_XXX.MODBYTES*8;
	public static final long P_OMASK=((long)(-1)<<(P_MBITS%BIG_XXX.BASEBITS));
	public static final long P_FEXCESS=((long)1<<(BIG_XXX.BASEBITS*BIG_XXX.NLEN-P_MBITS));
	public static final int P_TBITS=(P_MBITS%BIG_XXX.BASEBITS);

	private final BIG_XXX[] v;
	private final int length;

/**************** 64-bit specific ************************/

	public static long EXCESS(BIG_XXX a)
	{
		return ((a.get(BIG_XXX.NLEN-1)&P_OMASK)>>(P_TBITS));
	}

/* Check if product causes excess */
	public static boolean pexceed(BIG_XXX a,BIG_XXX b)
	{
		long ea,eb;
		ea=EXCESS(a);
		eb=EXCESS(b);
		if ((ea+1)>P_FEXCESS/(eb+1)) return true;
		return false;
	}

/* Check if square causes excess */
	public static boolean sexceed(BIG_XXX a)
	{
		long ea;
		ea=EXCESS(a);
		if ((ea+1)>P_FEXCESS/(ea+1)) return true;
		return false;
	}

/******************************************************/

/* Constructors */
	public FF_WWW(int n)
	{
		v=new BIG_XXX[n];
		for (int i=0;i<n;i++)
			v[i]=new BIG_XXX(0);
		length=n;
	}

	public int getlen()
	{
		return length;
	}

/* set to integer */
	public void set(int m)
	{
		zero();
		v[0].set(0,(m&BIG_XXX.BMASK));
		v[0].set(1,(m>>BIG_XXX.BASEBITS));
	}

/* copy from FF b */
	public void copy(FF_WWW b)
	{
		for (int i=0;i<length;i++)
		{
			v[i].copy(b.v[i]);
		}
	}

/* x=y<<n */
	public void dsucopy(FF_WWW b)
	{
		for (int i=0;i<b.length;i++)
		{
			v[b.length+i].copy(b.v[i]);
			v[i].zero();
		}
	}

/* x=y */
	public void dscopy(FF_WWW b)
	{
		for (int i=0;i<b.length;i++)
		{
			v[i].copy(b.v[i]);
			v[b.length+i].zero();
		}
	}

/* x=y>>n */
	public void sducopy(FF_WWW b)
	{
		for (int i=0;i<length;i++)
		{
			v[i].copy(b.v[length+i]);
		}
	}

/* set to zero */
	public void zero()
	{
		for (int i=0;i<length;i++)
		{
			v[i].zero();
		}
	}

	public void one()
	{
		v[0].one();
		for (int i=1;i<length;i++)
		{
			v[i].zero();
		}
	}

/* test equals 0 */
	public boolean iszilch() 
	{
		for (int i=0;i<length;i++)
		{
			if (!v[i].iszilch()) return false;
		}
		return true;
	}

/* shift right by BIGBITS-bit words */
	public void shrw(int n)
	{
		for (int i=0;i<n;i++) 
		{
			v[i].copy(v[i+n]);
			v[i+n].zero();
		}
	}

/* shift left by BIGBITS-bit words */
	public void shlw(int n)
	{
		for (int i=0;i<n;i++) 
		{
			v[n+i].copy(v[i]);
			v[i].zero();
		}
	}

/* extract last bit */
	public int parity()
	{
		return v[0].parity();
	}

	public int lastbits(int m)
	{
		return v[0].lastbits(m);
	}

/* compare x and y - must be normalised, and of same length */
	public static int comp(FF_WWW a,FF_WWW b)
	{
		int i,j;
		for (i=a.length-1;i>=0;i--)
		{
			j=BIG_XXX.comp(a.v[i],b.v[i]);
			if (j!=0) return j;
		}
		return 0;
	}

/* recursive add */
	public void radd(int vp,FF_WWW x,int xp,FF_WWW y,int yp,int n)
	{
		for (int i=0;i<n;i++)
		{
			v[vp+i].copy(x.v[xp+i]);
			v[vp+i].add(y.v[yp+i]);
		}
	}

/* recursive inc */
	public void rinc(int vp,FF_WWW y,int yp,int n)
	{
		for (int i=0;i<n;i++)
		{
			v[vp+i].add(y.v[yp+i]);
		}
	}

/* recursive sub */
	public void rsub(int vp,FF_WWW x,int xp,FF_WWW y,int yp,int n)
	{
		for (int i=0;i<n;i++)
		{
			v[vp+i].copy(x.v[xp+i]);
			v[vp+i].sub(y.v[yp+i]);
		}
	}

/* recursive dec */
	public void rdec(int vp,FF_WWW y,int yp,int n)
	{
		for (int i=0;i<n;i++)
		{
			v[vp+i].sub(y.v[yp+i]);
		}
	}

/* simple add */
	public void add(FF_WWW b)
	{
		for (int i=0;i<length;i++)
			v[i].add(b.v[i]);
	}

/* simple sub */
	public void sub(FF_WWW b)
	{
		for (int i=0;i<length;i++)
			v[i].sub(b.v[i]);
	}
	
/* reverse sub */
	public void revsub(FF_WWW b)
	{
		for (int i=0;i<length;i++)
			v[i].rsub(b.v[i]);
	}

/* increment/decrement by a small integer */
	public void inc(int m)
	{
		v[0].inc(m);
		norm();
	}

	public void dec(int m)
	{
		v[0].dec(m);
		norm();
	}

	/* normalise - but hold any overflow in top part unless n<0 */
	private void rnorm(int vp,int n)
	{
		boolean trunc=false;
		int i;
		long carry;
		if (n<0)
		{ /* -v n signals to do truncation */
			n=-n;
			trunc=true;
		}
		for (i=0;i<n-1;i++)
		{
			carry=v[vp+i].norm();  
			v[vp+i].xortop(carry<<P_TBITS);
			v[vp+i+1].incl(carry);
		}
		carry=v[vp+n-1].norm();
		if (trunc) 
			v[vp+n-1].xortop(carry<<P_TBITS);
	}

	public void norm()
	{
		rnorm(0,length);
	}

/* shift left by one bit */
	public void shl()
	{
		int i,carry,delay_carry=0;
		for (i=0;i<length-1;i++)
		{
			carry=v[i].fshl(1);
			v[i].inc(delay_carry);
			v[i].xortop((long)carry<<P_TBITS);
			delay_carry=carry;
		}
		v[length-1].fshl(1);
		v[length-1].inc(delay_carry);
	}

/* shift right by one bit */

	public void shr()
	{
		int carry;
		for (int i=length-1;i>0;i--)
		{
			carry=v[i].fshr(1);
			v[i-1].xortop((long)carry<<P_TBITS);
		}
		v[0].fshr(1);
	}

/* Convert to Hex String */
	public String toString() 
	{
		norm();
		String s="";
		for (int i=length-1;i>=0;i--)
		{
			s+=v[i].toString(); //s+=" ";
		}
		return s;
	}

/*
	public String toRawString(int len) 
	{
	//	norm(len);
		String s="";
		for (int i=len-1;i>=0;i--)
		{
			s+=v[i].toRawString(); s+=" ";
		}
		return s;
	}
*/
/* Convert FFs to/from byte arrays */
	public void toBytes(byte[] b)
	{
		for (int i=0;i<length;i++)
		{
			v[i].tobytearray(b,(length-i-1)*BIG_XXX.MODBYTES);
		}
	}

	public static void fromBytes(FF_WWW x,byte[] b)
	{
		for (int i=0;i<x.length;i++)
		{
			x.v[i]=BIG_XXX.frombytearray(b,(x.length-i-1)*BIG_XXX.MODBYTES);
		}
	}

/* in-place swapping using xor - side channel resistant - lengths must be the same */
	private static void cswap(FF_WWW a,FF_WWW b,int d)
	{
		for (int i=0;i<a.length;i++)
		{
		//	BIG_XXX.cswap(a.v[i],b.v[i],d);
			a.v[i].cswap(b.v[i],d);
		}
	}

/* z=x*y, t is workspace */
	private void karmul(int vp,FF_WWW x,int xp,FF_WWW y,int yp,FF_WWW t,int tp,int n)
	{
		int nd2;
		if (n==1)
		{
			DBIG_XXX d=BIG_XXX.mul(x.v[xp],y.v[yp]);
			v[vp+1]=d.split(8*BIG_XXX.MODBYTES);
			v[vp].copy(d);
			return;
		}
		nd2=n/2;
		radd(vp,x,xp,x,xp+nd2,nd2);
		rnorm(vp,nd2);                   /* Important - required for 32-bit build */
		radd(vp+nd2,y,yp,y,yp+nd2,nd2);
		rnorm(vp+nd2,nd2);               /* Important - required for 32-bit build */

		t.karmul(tp,this,vp,this,vp+nd2,t,tp+n,nd2);
		karmul(vp,x,xp,y,yp,t,tp+n,nd2);
		karmul(vp+n,x,xp+nd2,y,yp+nd2,t,tp+n,nd2);
		t.rdec(tp,this,vp,n);
		t.rdec(tp,this,vp+n,n);
		rinc(vp+nd2,t,tp,n);
		rnorm(vp,2*n);
	}

	private void karsqr(int vp,FF_WWW x,int xp,FF_WWW t,int tp,int n)
	{
		int nd2;
		if (n==1)
		{
			DBIG_XXX d=BIG_XXX.sqr(x.v[xp]);
			v[vp+1].copy(d.split(8*BIG_XXX.MODBYTES));
			v[vp].copy(d);
			return;
		}	

		nd2=n/2;
		karsqr(vp,x,xp,t,tp+n,nd2);
		karsqr(vp+n,x,xp+nd2,t,tp+n,nd2);
		t.karmul(tp,x,xp,x,xp+nd2,t,tp+n,nd2);
		rinc(vp+nd2,t,tp,n);
		rinc(vp+nd2,t,tp,n);
		rnorm(vp+nd2,n);
	}


	private void karmul_lower(int vp,FF_WWW x,int xp,FF_WWW y,int yp,FF_WWW t,int tp,int n)
	{ /* Calculates Least Significant bottom half of x*y */
		int nd2;
		if (n==1)
		{ /* only calculate bottom half of product */
			v[vp].copy(BIG_XXX.smul(x.v[xp],y.v[yp]));
			return;
		}
		nd2=n/2;
		karmul(vp,x,xp,y,yp,t,tp+n,nd2);
		t.karmul_lower(tp,x,xp+nd2,y,yp,t,tp+n,nd2);
		rinc(vp+nd2,t,tp,nd2);
		t.karmul_lower(tp,x,xp,y,yp+nd2,t,tp+n,nd2);

		rinc(vp+nd2,t,tp,nd2);
		rnorm(vp+nd2,-nd2);  /* truncate it */
	}

	private void karmul_upper(FF_WWW x,FF_WWW y,FF_WWW t,int n)
	{ /* Calculates Most Significant upper half of x*y, given lower part */
		int nd2;
 
		nd2=n/2;
		radd(n,x,0,x,nd2,nd2);
		radd(n+nd2,y,0,y,nd2,nd2);
		rnorm(n,nd2);
		rnorm(n+nd2,nd2);

		t.karmul(0,this,n+nd2,this,n,t,n,nd2);  /* t = (a0+a1)(b0+b1) */
		karmul(n,x,nd2,y,nd2,t,n,nd2); /* z[n]= a1*b1 */
									/* z[0-nd2]=l(a0b0) z[nd2-n]= h(a0b0)+l(t)-l(a0b0)-l(a1b1) */
		t.rdec(0,this,n,n);              /* t=t-a1b1  */
		rinc(nd2,this,0,nd2);   /* z[nd2-n]+=l(a0b0) = h(a0b0)+l(t)-l(a1b1)  */
		rdec(nd2,t,0,nd2);   /* z[nd2-n]=h(a0b0)+l(t)-l(a1b1)-l(t-a1b1)=h(a0b0) */
		rnorm(0,-n);					/* a0b0 now in z - truncate it */
		t.rdec(0,this,0,n);         /* (a0+a1)(b0+b1) - a0b0 */
		rinc(nd2,t,0,n);

		rnorm(nd2,n);
	}

	/* z=x*y. Assumes x and y are of same length. */
	public static FF_WWW mul(FF_WWW x,FF_WWW y)
	{
		int n=x.length;
		FF_WWW z=new FF_WWW(2*n);
		FF_WWW t=new FF_WWW(2*n);
//		x.norm(); y.norm();
		z.karmul(0,x,0,y,0,t,0,n);
		return z;
	}

	/* z=x^2 */
	public static FF_WWW sqr(FF_WWW x)
	{
		int n=x.length;
		FF_WWW z=new FF_WWW(2*n);
		FF_WWW t=new FF_WWW(2*n);
//		x.norm(); 
		z.karsqr(0,x,0,t,0,n);
		return z;
	}

/* return low part of product this*y */
	public void lmul(FF_WWW y)
	{
		int n=length;
		FF_WWW t=new FF_WWW(2*n);
		FF_WWW x=new FF_WWW(n); x.copy(this);
//		x.norm(); y.norm();
		karmul_lower(0,x,0,y,0,t,0,n);
	}

/* Set b=b mod c */
	public void mod(FF_WWW c)
	{
		int k=0;  

		norm();
		if (comp(this,c)<0) 
			return;
		do
		{
			c.shl();
			k++;
		} while (comp(this,c)>=0);

		while (k>0)
		{
			c.shr();
			if (comp(this,c)>=0)
			{
				sub(c);
				norm();
			}
			k--;
		}
	}

/* return This mod modulus, N is modulus, ND is Montgomery Constant */
	public FF_WWW reduce(FF_WWW N,FF_WWW ND)
	{ /* fast karatsuba Montgomery reduction */
		int n=N.length;
		FF_WWW t=new FF_WWW(2*n);
		FF_WWW r=new FF_WWW(n);
		FF_WWW m=new FF_WWW(n);

		r.sducopy(this);
		m.karmul_lower(0,this,0,ND,0,t,0,n);
		karmul_upper(N,m,t,n);
		m.sducopy(this);

		r.add(N);
		r.sub(m);
		r.norm();

		return r;
	}

/* Set r=this mod b */
/* this is of length - 2*n */
/* r,b is of length - n */
	public FF_WWW dmod(FF_WWW b)
	{
		int k,n=b.length;
		FF_WWW m=new FF_WWW(2*n);
		FF_WWW x=new FF_WWW(2*n);
		FF_WWW r=new FF_WWW(n);

		x.copy(this);
		x.norm();
		m.dsucopy(b); k=BIG_XXX.BIGBITS*n;

		while (k>0)
		{	
			m.shr();

			if (comp(x,m)>=0)
			{
				x.sub(m);
				x.norm();
			}
			k--;
		}

		r.copy(x);
		r.mod(b);
		return r;
	}

/* Set return=1/this mod p. Binary method - a<p on entry */

	public void invmodp(FF_WWW p)
	{
		int n=p.length;

		FF_WWW u=new FF_WWW(n);
		FF_WWW v=new FF_WWW(n);
		FF_WWW x1=new FF_WWW(n);
		FF_WWW x2=new FF_WWW(n);
		FF_WWW t=new FF_WWW(n);
		FF_WWW one=new FF_WWW(n);

		one.one();
		u.copy(this);
		v.copy(p);
		x1.copy(one);
		x2.zero();

	// reduce n in here as well! 
		while (comp(u,one)!=0 && comp(v,one)!=0)
		{
			while (u.parity()==0)
			{
				u.shr();
				if (x1.parity()!=0)
				{
					x1.add(p); 
					x1.norm();
				}
				x1.shr(); 
			}
			while (v.parity()==0)
			{
				v.shr(); 
				if (x2.parity()!=0)
				{
					x2.add(p);
					x2.norm();
				}
				x2.shr();
			}
			if (comp(u,v)>=0)
			{

				u.sub(v);
				u.norm();
				if (comp(x1,x2)>=0) x1.sub(x2);
				else
				{
					t.copy(p);
					t.sub(x2);
					x1.add(t);
				}
				x1.norm();
			}
			else
			{
				v.sub(u);
				v.norm();
				if (comp(x2,x1)>=0) x2.sub(x1);
				else
				{
					t.copy(p);
					t.sub(x1);
					x2.add(t);
				}
				x2.norm();
			}
		}
		if (comp(u,one)==0)
			copy(x1);
		else
			copy(x2);
	}

/* nresidue mod m */
	public void nres(FF_WWW m)
	{
		int n=m.length;
		if (n==1)
		{ 
			DBIG_XXX d=new DBIG_XXX(this.v[0]);
			d.shl(BIG_XXX.NLEN*BIG_XXX.BASEBITS);
			this.v[0].copy(d.mod(m.v[0]));
		}
		else
		{
			FF_WWW d=new FF_WWW(2*n);
			d.dsucopy(this);
			copy(d.dmod(m));
		}
	}

	public void redc(FF_WWW m,FF_WWW ND)
	{
		int n=m.length;
		if (n==1)
		{
			DBIG_XXX d=new DBIG_XXX(this.v[0]);
			this.v[0].copy(BIG_XXX.monty(m.v[0],(BIG_XXX.cast_to_chunk(1)<<BIG_XXX.BASEBITS)-ND.v[0].w[0],d));
		}
		else
		{
			FF_WWW d=new FF_WWW(2*n);
			mod(m);
			d.dscopy(this);
			copy(d.reduce(m,ND));
			mod(m);
		}
	}

	private void mod2m(int m)
	{
		for (int i=m;i<length;i++)
			v[i].zero();
	}

	/* U=1/a mod 2^m - Arazi & Qi */
	private FF_WWW invmod2m()
	{
		int i,n=length;

		FF_WWW b=new FF_WWW(n);
		FF_WWW c=new FF_WWW(n);
		FF_WWW U=new FF_WWW(n);
		FF_WWW t;

		U.zero();
		U.v[0].copy(v[0]);
		U.v[0].invmod2m();

		for (i=1;i<n;i<<=1)
		{
			b.copy(this); b.mod2m(i);
			t=mul(U,b);

			t.shrw(i); b.copy(t);
			c.copy(this); c.shrw(i); c.mod2m(i);
			c.lmul(U); c.mod2m(i);

			b.add(c); b.norm();
			b.lmul(U); b.mod2m(i);

			c.one(); c.shlw(i); b.revsub(c); b.norm();
			b.shlw(i);
			U.add(b);
		}
		U.norm();
		return U;
	}

	public void random(RAND rng)
	{
		int n=length;
		for (int i=0;i<n;i++)
		{
			v[i].copy(BIG_XXX.random(rng));
		}
	/* make sure top bit is 1 */
		while (v[n-1].nbits()<BIG_XXX.MODBYTES*8) v[n-1].copy(BIG_XXX.random(rng));
	}

	/* generate random x */
	public void randomnum(FF_WWW p,RAND rng)
	{
		int n=length;
		FF_WWW d=new FF_WWW(2*n);

		for (int i=0;i<2*n;i++)
		{
			d.v[i].copy(BIG_XXX.random(rng));
		}
		copy(d.dmod(p));
	}

	/* this*=y mod p */
	public void modmul(FF_WWW y,FF_WWW p,FF_WWW nd)
	{
		if (pexceed(v[length-1],y.v[y.length-1])) mod(p);
		int n=p.length;
		if (n==1)
		{
			DBIG_XXX d=BIG_XXX.mul(this.v[0],y.v[0]);
			this.v[0].copy(BIG_XXX.monty(p.v[0],(BIG_XXX.cast_to_chunk(1)<<BIG_XXX.BASEBITS)-nd.v[0].w[0],d));
		}
		else
		{
			FF_WWW d=mul(this,y);
			copy(d.reduce(p,nd));
		}
	}

	/* this*=y mod p */
	public void modsqr(FF_WWW p,FF_WWW nd)
	{
		if (sexceed(v[length-1])) mod(p);
		int n=p.length;
		if (n==1)
		{
			DBIG_XXX d=BIG_XXX.sqr(this.v[0]);
			this.v[0].copy(BIG_XXX.monty(p.v[0],(BIG_XXX.cast_to_chunk(1)<<BIG_XXX.BASEBITS)-nd.v[0].w[0],d));

		}
		else
		{
			FF_WWW d=sqr(this);
			copy(d.reduce(p,nd));
		}
	}

	/* this=this^e mod p using side-channel resistant Montgomery Ladder, for large e */
	public void skpow(FF_WWW e,FF_WWW p)
	{
		int i,b,n=p.length;
		FF_WWW R0=new FF_WWW(n);
		FF_WWW R1=new FF_WWW(n);
		FF_WWW ND=p.invmod2m();

		mod(p);
		R0.one();
		R1.copy(this);
		R0.nres(p);
		R1.nres(p);

		for (i=8*BIG_XXX.MODBYTES*n-1;i>=0;i--)
		{
			b=e.v[i/BIG_XXX.BIGBITS].bit(i%BIG_XXX.BIGBITS);
			copy(R0);
			modmul(R1,p,ND);

			cswap(R0,R1,b);
			R0.modsqr(p,ND);

			R1.copy(this);
			cswap(R0,R1,b);
		}
		copy(R0);
		redc(p,ND);
	}

	/* this =this^e mod p using side-channel resistant Montgomery Ladder, for short e */
	public void skpow(BIG_XXX e,FF_WWW p)
	{
		int i,b,n=p.length;
		FF_WWW R0=new FF_WWW(n);
		FF_WWW R1=new FF_WWW(n);
		FF_WWW ND=p.invmod2m();

		mod(p);
		R0.one();
		R1.copy(this);
		R0.nres(p);
		R1.nres(p);

		for (i=8*BIG_XXX.MODBYTES-1;i>=0;i--)
		{
			b=e.bit(i);
			copy(R0);
			modmul(R1,p,ND);

			cswap(R0,R1,b);
			R0.modsqr(p,ND);

			R1.copy(this);
			cswap(R0,R1,b);
		}
		copy(R0);
		redc(p,ND);
	}

	/* raise to an integer power - right-to-left method */
	public void power(int e,FF_WWW p)
	{
		int n=p.length;
		FF_WWW w=new FF_WWW(n);
		FF_WWW ND=p.invmod2m();
		boolean f=true;

		w.copy(this);
		w.nres(p);

		if (e==2)
		{
			copy(w);
			modsqr(p,ND);
		}
		else for (; ; )
		{
			if (e%2==1)
			{
				if (f) copy(w);
				else modmul(w,p,ND);
				f=false;
			}
			e>>=1;
			if (e==0) break;
			w.modsqr(p,ND);
		}
		redc(p,ND);
	}

	/* this=this^e mod p, faster but not side channel resistant */
	public void pow(FF_WWW e,FF_WWW p)
	{
		int i,b,n=p.length;
		FF_WWW w=new FF_WWW(n);
		FF_WWW ND=p.invmod2m();

		w.copy(this);
		one();
		nres(p);
		w.nres(p);
		for (i=8*BIG_XXX.MODBYTES*n-1;i>=0;i--)
		{
			modsqr(p,ND);
			b=e.v[i/BIG_XXX.BIGBITS].bit(i%BIG_XXX.BIGBITS);
			if (b==1) modmul(w,p,ND);
		}
		redc(p,ND);
	}

	/* double exponentiation r=x^e.y^f mod p */
	public void pow2(BIG_XXX e,FF_WWW y,BIG_XXX f,FF_WWW p)
	{
		int i,eb,fb,n=p.length;
		FF_WWW xn=new FF_WWW(n);
		FF_WWW yn=new FF_WWW(n);
		FF_WWW xy=new FF_WWW(n);
		FF_WWW ND=p.invmod2m();

		xn.copy(this);
		yn.copy(y);
		xn.nres(p);
		yn.nres(p);
		xy.copy(xn); xy.modmul(yn,p,ND);
		one();
		nres(p);

		for (i=8*BIG_XXX.MODBYTES-1;i>=0;i--)
		{
			eb=e.bit(i);
			fb=f.bit(i);
			modsqr(p,ND);
			if (eb==1)
			{
				if (fb==1) modmul(xy,p,ND);
				else modmul(xn,p,ND);
			}
			else
			{
				if (fb==1) modmul(yn,p,ND);
			}
		}
		redc(p,ND);
	}

	private static int igcd(int x,int y)
	{ /* integer GCD, returns GCD of x and y */
		int r;
		if (y==0) return x;
		while ((r=x%y)!=0)
			{x=y;y=r;}
		return y;
	}

	/* quick and dirty check for common factor with n */
	public boolean cfactor(int s)
	{
		int r,n=length;
		int g;

		FF_WWW x=new FF_WWW(n);
		FF_WWW y=new FF_WWW(n);

		y.set(s);
		x.copy(this);
		x.norm();

		do
		{
			x.sub(y);
			x.norm();
			while (!x.iszilch() && x.parity()==0) x.shr();
		}
		while (comp(x,y)>0);

		g=(int)x.v[0].get(0);
		r=igcd(s,g);
		if (r>1) return true;
		return false;
	}

	/* Miller-Rabin test for primality. Slow. */
	public static boolean prime(FF_WWW p,RAND rng)
	{
		int i,j,s=0,n=p.length;
		boolean loop;
		FF_WWW d=new FF_WWW(n);
		FF_WWW x=new FF_WWW(n);
		FF_WWW unity=new FF_WWW(n);
		FF_WWW nm1=new FF_WWW(n);

		int sf=4849845; /* 3*5*.. *19 */
		p.norm();

		if (p.cfactor(sf)) return false;
		unity.one();
		nm1.copy(p);
		nm1.sub(unity);
		nm1.norm();
		d.copy(nm1);

		while (d.parity()==0)
		{
			d.shr();
			s++;
		}
		if (s==0) return false;
		for (i=0;i<10;i++)
		{
			x.randomnum(p,rng);
			x.pow(d,p);

			if (comp(x,unity)==0 || comp(x,nm1)==0) continue;
			loop=false;
			for (j=1;j<s;j++)
			{
				x.power(2,p);
				if (comp(x,unity)==0) return false;
				if (comp(x,nm1)==0) {loop=true; break;}
			}
			if (loop) continue;
			return false;
		}
		return true;
	}

}