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

/* AMCL double length DBIG number class */ 

package amcl.XXX;

public class DBIG {
	protected int[] w=new int[BIG.DNLEN];

/* normalise this */
	public void norm() {
		int d,carry=0;
		for (int i=0;i<BIG.DNLEN-1;i++)
		{
			d=w[i]+carry;
			carry=d>>BIG.BASEBITS;
			w[i]=d&BIG.BMASK;
		}
		w[BIG.DNLEN-1]=(w[BIG.DNLEN-1]+carry);
	}


/*
	public String toRawString()
	{
		DBIG b=new DBIG(this);
		String s="(";
		for (int i=0;i<BIG.DNLEN-1;i++)
		{
			s+=Integer.toHexString(b.w[i]); s+=",";
		}
		s+=Integer.toHexString(b.w[BIG.DNLEN-1]); s+=")";
		return s;
	}
*/


/****************************************************************************/


/* return number of bits in this */
	public int nbits() {
		int bts,k=BIG.DNLEN-1;
		long c;
		norm();
		while (w[k]==0 && k>=0) k--;
		if (k<0) return 0;
		bts=BIG.BASEBITS*k;
		c=w[k];
		while (c!=0) {c/=2; bts++;}
		return bts;
	}

/* convert this to string */
	public String toString() {
		DBIG b;
		String s="";
		int len=nbits();
		if (len%4==0) len>>=2; //len/=4;
		else {len>>=2; len++;}

		for (int i=len-1;i>=0;i--)
		{
			b=new DBIG(this);
			b.shr(i*4);
			s+=Integer.toHexString((int)(b.w[0]&15));
		}
		return s;
	}

	public void cmove(DBIG g,int d)
	{
		int i;
	//	int b=-d;

		for (i=0;i<BIG.DNLEN;i++)
		{
			w[i]^=(w[i]^g.w[i])&BIG.cast_to_chunk(-d);
		}
	}

/* Constructors */
	public DBIG(int x)
	{
		w[0]=x;
		for (int i=1;i<BIG.DNLEN;i++)
			w[i]=0;
	}

	public DBIG(DBIG x)
	{
		for (int i=0;i<BIG.DNLEN;i++)
			w[i]=x.w[i];
	}

	public DBIG(BIG x)
	{
		for (int i=0;i<BIG.NLEN-1;i++)
			w[i]=x.w[i];//get(i);

		w[BIG.NLEN-1]=x.w[(BIG.NLEN-1)]&BIG.BMASK; /* top word normalized */
		w[BIG.NLEN]=(x.w[(BIG.NLEN-1)]>>BIG.BASEBITS);

		for (int i=BIG.NLEN+1;i<BIG.DNLEN;i++) w[i]=0;
	}

/* split DBIG at position n, return higher half, keep lower half */
	public BIG split(int n)
	{
		BIG t=new BIG(0);
		int nw,m=n%BIG.BASEBITS;
		int carry=w[BIG.DNLEN-1]<<(BIG.BASEBITS-m);

		for (int i=BIG.DNLEN-2;i>=BIG.NLEN-1;i--)
		{
			nw=(w[i]>>m)|carry;
			carry=(w[i]<<(BIG.BASEBITS-m))&BIG.BMASK;
			t.w[i-BIG.NLEN+1]=nw;
		//	t.set(i-BIG.NLEN+1,nw);
		}
		w[BIG.NLEN-1]&=(((int)1<<m)-1);
		return t;
	}

/* Copy from another DBIG */
	public void copy(DBIG x)
	{
		for (int i=0;i<BIG.DNLEN;i++)
			w[i]=x.w[i];
	}

/* test this=0? */
	public boolean iszilch() {
		for (int i=0;i<BIG.DNLEN;i++)
			if (w[i]!=0) return false;
		return true; 
	}

/* shift this right by k bits */
	public void shr(int k) {
		int n=k%BIG.BASEBITS;
		int m=k/BIG.BASEBITS;	
		for (int i=0;i<BIG.DNLEN-m-1;i++)
			w[i]=(w[m+i]>>n)|((w[m+i+1]<<(BIG.BASEBITS-n))&BIG.BMASK);
		w[BIG.DNLEN-m-1]=w[BIG.DNLEN-1]>>n;
		for (int i=BIG.DNLEN-m;i<BIG.DNLEN;i++) w[i]=0;
	}

/* shift this left by k bits */
	public void shl(int k) {
		int n=k%BIG.BASEBITS;
		int m=k/BIG.BASEBITS;

		w[BIG.DNLEN-1]=((w[BIG.DNLEN-1-m]<<n))|(w[BIG.DNLEN-m-2]>>(BIG.BASEBITS-n));
		for (int i=BIG.DNLEN-2;i>m;i--)
			w[i]=((w[i-m]<<n)&BIG.BMASK)|(w[i-m-1]>>(BIG.BASEBITS-n));
		w[m]=(w[0]<<n)&BIG.BMASK; 
		for (int i=0;i<m;i++) w[i]=0;
	}

/* this+=x */
	public void add(DBIG x) {
		for (int i=0;i<BIG.DNLEN;i++)
			w[i]+=x.w[i];	
	}

/* this-=x */
	public void sub(DBIG x) {
		for (int i=0;i<BIG.DNLEN;i++)
			w[i]-=x.w[i];
	}

/* Compare a and b, return 0 if a==b, -1 if a<b, +1 if a>b. Inputs must be normalised */
	public static int comp(DBIG a,DBIG b)
	{
		for (int i=BIG.DNLEN-1;i>=0;i--)
		{
			if (a.w[i]==b.w[i]) continue;
			if (a.w[i]>b.w[i]) return 1;
			else  return -1;
		}
		return 0;
	}

/* reduces this DBIG mod a BIG, and returns the BIG */
	public BIG mod(BIG c)
	{
		int k=0;  
		norm();
		DBIG m=new DBIG(c);
		DBIG r=new DBIG(0);

		if (comp(this,m)<0) return new BIG(this);
		
		do
		{
			m.shl(1);
			k++;
		}
		while (comp(this,m)>=0);

		while (k>0)
		{
			m.shr(1);

			r.copy(this);
			r.sub(m);
			r.norm();
			cmove(r,(int)(1-((r.w[BIG.DNLEN-1]>>(BIG.CHUNK-1))&1)));
/*
			if (comp(this,m)>=0)
			{
				sub(m);
				norm();
			}
*/
			k--;
		}
		return new BIG(this);
	}

/* reduces this DBIG mod a DBIG in place */
/*	public void mod(DBIG m)
	{
		int k=0;
		if (comp(this,m)<0) return;

		do
		{
			m.shl(1);
			k++;
		}
		while (comp(this,m)>=0);

		while (k>0)
		{
			m.shr(1);
			if (comp(this,m)>=0)
			{
				sub(m);
				norm();
			}
			k--;
		}
		return;

	}*/

/* return this/c */
	public BIG div(BIG c)
	{
		int d,k=0;
		DBIG m=new DBIG(c);
		DBIG dr=new DBIG(0);
		BIG r=new BIG(0);
		BIG a=new BIG(0);
		BIG e=new BIG(1);
		norm();

		while (comp(this,m)>=0)
		{
			e.fshl(1);
			m.shl(1);
			k++;
		}

		while (k>0)
		{
			m.shr(1);
			e.shr(1);

			dr.copy(this);
			dr.sub(m);
			dr.norm();
			d=(int)(1-((dr.w[BIG.DNLEN-1]>>(BIG.CHUNK-1))&1));
			cmove(dr,d);
			r.copy(a);
			r.add(e);
			r.norm();
			a.cmove(r,d);
/*
			if (comp(this,m)>0)
			{
				a.add(e);
				a.norm();
				sub(m);
				norm();
			} */
			k--;
		}
		return a;
	}
}
