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

/* Fixed Data in ROM - Field and Curve parameters */


package amcl.MF256M;

public class ROM
{
// 256MF Modulus

	public static final long[] Modulus= {0xFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFL,0xFFA7FFFFL};
	public static final long MConst=0xFFA80000L;

// MF256M Curve

	public static final int CURVE_A =-54314;
	public static final long[] CURVE_B = {0x0L,0x0L,0x0L,0x0L,0x0L}; // not used
	public static final long[] CURVE_Order={0xD92EDED8EC7BABL,0xBBAFB86733C966L,0xFFFFFFFFFFB154L,0xFFFFFFFFFFFFFFL,0x3FE9FFFFL};
	public static final long[] CURVE_Gx ={0x8L,0x0L,0x0L,0x0L,0x0L};
	public static final long[] CURVE_Gy ={0x0L,0x0L,0x0L,0x0L,0x0L}; // not used

}

