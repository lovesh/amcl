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


package amcl.NIST521;

public class ROM
{
// NIST521 Modulus
	public static final int[] Modulus={0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0x1FFFF};
	public static final int MConst=0x1;

// NIST521 Curve

	public static final int CURVE_A=-3;
	public static final int[] CURVE_Order = {0x1386409,0x6FB71E9,0xC47AEBB,0xC9B8899,0x5D03BB5,0x48F709A,0xB7FCC01,0xBF2F966,0x1868783,0xFFFFFA5,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0xFFFFFFF,0x1FFFF};
	public static final int[] CURVE_B = {0xB503F00,0x451FD46,0xC34F1EF,0xDF883D2,0xF073573,0xBD3BB1B,0xB1652C0,0xEC7E937,0x6193951,0xF109E15,0x489918E,0x15F3B8B,0x25B99B3,0xEEA2DA7,0xB68540,0x929A21A,0xE1C9A1F,0x3EB9618,0x5195};
	public static final int[] CURVE_Gx = {0x2E5BD66,0x7E7E31C,0xA429BF9,0xB3C1856,0x8DE3348,0x27A2FFA,0x8FE1DC1,0xEFE7592,0x14B5E77,0x4D3DBAA,0x8AF606B,0xB521F82,0x139053F,0x429C648,0x62395B4,0x9E3ECB6,0x404E9CD,0x8E06B70,0xC685};
	public static final int[] CURVE_Gy = {0xFD16650,0xBE94769,0x2C24088,0x7086A27,0x761353C,0x13FAD0,0xC550B9,0x5EF4264,0x7EE7299,0x3E662C9,0xFBD1727,0x446817A,0x449579B,0xD998F54,0x42C7D1B,0x5C8A5FB,0xA3BC004,0x296A789,0x11839};

}
