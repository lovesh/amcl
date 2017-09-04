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

//
//  rom.swift
//
//  Created by Michael Scott on 12/06/2015.
//  Copyright (c) 2015 Michael Scott. All rights reserved.
//

final public class ROM{
 
#if D32

// Base Bits= 29
//  nums512 Modulus
static let Modulus:[Chunk] = [0x1FFFFDC7,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x7FFFF]
static let R2modp:[Chunk] = [0xB100000,0x278,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0]
static let MConst:Chunk = 0x239

//  nums512 Weierstrass Curve
static let CURVE_Cof:Int = 1
static let CURVE_A:Int = -3
static let CURVE_B_I:Int = 121243
static let CURVE_B:[Chunk] = [0x1D99B,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0]
static public let CURVE_Order:[Chunk] = [0x433555D,0x10A9F9C8,0x1F3490F3,0xD166CC0,0xBDC63B5,0xC76CBE8,0xC6D3F09,0x1F729CF0,0x1F5B3CA4,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x1FFFFFFF,0x7FFFF]
static public let CURVE_Gx:[Chunk] = [0xCABAE57,0x4143CAC,0x1BD778B7,0x1AC026FA,0x15831D5,0x14312AB,0x167A4DE5,0xA20ED66,0x195021A1,0x129836CF,0x1141B830,0xA03ED0A,0xCAD83BB,0x1E9DA94C,0xDC00A80,0x1527B45,0x1447141D,0x1D601]
static public let CURVE_Gy:[Chunk] = [0x183527A6,0x1D043B01,0x1F43FA48,0x16B83C99,0x5602CF2,0x1420592D,0x17A70486,0x1B5161DD,0x14A28415,0x3DE8A78,0x3D2C983,0x17797719,0x197DBDEA,0x15D88025,0x1BBB718F,0xAD679C1,0x14CA29AD,0x4A1D2]


#endif

#if D64

// Base Bits= 60
//  nums512 Modulus
static let Modulus:[Chunk] = [0xFFFFFFFFFFFFDC7,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFF]
static let R2modp:[Chunk] = [0x100000000000000,0x4F0B,0x0,0x0,0x0,0x0,0x0,0x0,0x0]
static let MConst:Chunk = 0x239

//  nums512 Weierstrass Curve
static let CURVE_Cof:Int = 1
static let CURVE_A:Int = -3
static let CURVE_B_I:Int = 121243
static let CURVE_B:[Chunk] = [0x1D99B,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0]
static public let CURVE_Order:[Chunk] = [0xE153F390433555D,0x568B36607CD243C,0x258ED97D0BDC63B,0xA4FB94E7831B4FC,0xFFFFFFFFFFF5B3C,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFFFFFFFFF,0xFFFFFFFF]
static public let CURVE_Gx:[Chunk] = [0xC8287958CABAE57,0x5D60137D6F5DE2D,0x94286255615831D,0xA151076B359E937,0xC25306D9F95021,0x3BB501F6854506E,0x2A03D3B5298CAD8,0x141D0A93DA2B700,0x3AC03447]
static public let CURVE_Gy:[Chunk] = [0x3A08760383527A6,0x2B5C1E4CFD0FE92,0x1A840B25A5602CF,0x15DA8B0EEDE9C12,0x60C7BD14F14A284,0xDEABBCBB8C8F4B2,0xC63EBB1004B97DB,0x29AD56B3CE0EEED,0x943A54CA]


#endif

}

