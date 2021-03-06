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

use fp512bn::big::NLEN;
use super::super::arch::Chunk;
use types::{ModType, CurveType, CurvePairingType, SexticTwist, SignOfX};

// Base Bits= 60
pub const MODULUS: [Chunk; NLEN] = [
    0x4EB280922ADEF33,
    0x6A55CE5F4C6467B,
    0xC65DEAB236FE191,
    0xCF1EACBE98B8E48,
    0x3C111B0EF455146,
    0xA1D8CB5307C0BBE,
    0xFFFF9EC7F01C60B,
    0xFFFFFFFFFFFFFFF,
    0xFFFFFFFF,
];
pub const R2MODP: [Chunk; NLEN] = [
    0x1FA6DCEF99812E9,
    0xAB3452895A0B74E,
    0xC53EA988C079E1E,
    0x1E90E033BA630B9,
    0xF1EA41C0714D8B0,
    0xE72785387509E28,
    0xD86794F834DAB00,
    0x9757C2ACCD342A1,
    0x44ECB079,
];
pub const MCONST: Chunk = 0x692A189FCCC5C05;

pub const CURVE_COF_I: isize = 1;
pub const CURVE_A: isize = 0;
pub const CURVE_B_I: isize = 3;
pub const CURVE_B: [Chunk; NLEN] = [0x3, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0];
pub const CURVE_ORDER: [Chunk; NLEN] = [
    0x6A64A5F519A09ED,
    0x10313E04F9A2B40,
    0xC65DEAB2679A34A,
    0xCF1EACBE98B8E48,
    0x3C111B0EF445146,
    0xA1D8CB5307C0BBE,
    0xFFFF9EC7F01C60B,
    0xFFFFFFFFFFFFFFF,
    0xFFFFFFFF,
];
pub const CURVE_GX: [Chunk; NLEN] = [0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0];
pub const CURVE_GY: [Chunk; NLEN] = [0x2, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0];

pub const FRA: [Chunk; NLEN] = [
    0x49617B1F4B73AB2,
    0x71514F6202AED1F,
    0xF6080D3BD8681E1,
    0xF8AA9E852CBBB59,
    0xC8CF2E2068398E9,
    0x8A5296F791AB26B,
    0x196A8C7C68B4EA1,
    0xCF5BBF9095A1B79,
    0x1EF71AA9,
];
pub const FRB: [Chunk; NLEN] = [
    0x5510572DF6B481,
    0xF9047EFD49B595C,
    0xD055DD765E95FAF,
    0xD6740E396BFD2EE,
    0x7341ECEE8C1B85C,
    0x1786345B7615952,
    0xE695124B876776A,
    0x30A4406F6A5E486,
    0xE108E556,
];
pub const CURVE_BNX: [Chunk; NLEN] = [
    0xB306BB5E1BD80F,
    0x82F5C030B0F7F01,
    0x68,
    0x0,
    0x0,
    0x0,
    0x0,
    0x0,
    0x0,
];
pub const CURVE_COF: [Chunk; NLEN] = [0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0];
pub const CURVE_CRU: [Chunk; NLEN] = [
    0xB0716209C79298A,
    0xCEE6799B8B17C14,
    0x78966BE526092AE,
    0x20089C27507ACD8,
    0xF8EF7611FA3074B,
    0x6146B86B378EA2C,
    0xFFFF9EC7DC83D2A,
    0xFFFFFFFFFFFFFFF,
    0xFFFFFFFF,
];
pub const CURVE_PXA: [Chunk; NLEN] = [
    0xF07A96E0DB646B5,
    0x18F87319072FFE8,
    0x7BE21BCBBC78F22,
    0x537863514DC6DC5,
    0xDA57CC78CD0B024,
    0xD29B358F0DB9B57,
    0x7412F3CEA1E4BBB,
    0xE138648958801BA,
    0x3B165339,
];
pub const CURVE_PXB: [Chunk; NLEN] = [
    0xDB5CBEFDA8AE0E9,
    0xCA411CD88911B3,
    0xD6E1383D5ADCE4,
    0x227285526E0D5E5,
    0xB02566B94D9781E,
    0x56DC6C6EF2476A8,
    0x680ABE8B4825EA6,
    0xF85067E6C89B4C4,
    0x481C13CB,
];
pub const CURVE_PYA: [Chunk; NLEN] = [
    0x2480312ADDE67A1,
    0xDA17AD615EFB85E,
    0x312542808B7BC5C,
    0x18BDEC153E8EDD2,
    0xE5C158699D4B6CD,
    0xB1DF660AFCDD03E,
    0xB0CBA374F277085,
    0xC827C7B8292EF5A,
    0x6F01EC84,
];
pub const CURVE_PYB: [Chunk; NLEN] = [
    0x58B7186C84F8E8B,
    0xF05C2224BF76168,
    0x10AD7EE279C08DF,
    0x7FC3E2E50714A43,
    0x3D04961941DA289,
    0x38C118867B0C9B6,
    0xC315F75D91F0214,
    0x8B04E7831AC3640,
    0x51A3BCEC,
];
pub const CURVE_W: [[Chunk; NLEN]; 2] = [
    [
        0x110F89749834583,
        0x65FB911D16A173F,
        0xFFFFFFFFCF63FE9,
        0xFFFFFFFFFFFFFFF,
        0xFFFF,
        0x0,
        0x0,
        0x0,
        0x0,
    ],
    [
        0x1660D76BC37B01F,
        0x5EB806161EFE02,
        0xD1,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
    ],
];
pub const CURVE_SB: [[[Chunk; NLEN]; 2]; 2] = [
    [
        [
            0xFAAEB208D4B9564,
            0x601010BBB4B193C,
            0xFFFFFFFFCF63F18,
            0xFFFFFFFFFFFFFFF,
            0xFFFF,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x5403CE8956259CE,
            0xA45BDA397B2D3E,
            0xC65DEAB2679A279,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
    ],
    [
        [
            0x1660D76BC37B01F,
            0x5EB806161EFE02,
            0xD1,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x110F89749834583,
            0x65FB911D16A173F,
            0xFFFFFFFFCF63FE9,
            0xFFFFFFFFFFFFFFF,
            0xFFFF,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
    ],
];
pub const CURVE_WB: [[Chunk; NLEN]; 4] = [
    [
        0x6DAB36AB55A29F0,
        0xFC42C60583D30C1,
        0x5555555545215FB,
        0x555555555555555,
        0x5555,
        0x0,
        0x0,
        0x0,
        0x0,
    ],
    [
        0xEEB012BA2355D4B,
        0xF20FC1FD7F84F17,
        0x892FA9DE2BB5E5C,
        0x74B96064DAD40F5,
        0xD76BC3535163152,
        0x806161EFE021660,
        0xD105EB,
        0x0,
        0x0,
    ],
    [
        0x7CF03F380289AAD,
        0xBA82C117183E70C,
        0xC497D4EF15DAF62,
        0x3A5CB0326D6A07A,
        0x6BB5E1A9A8B18A9,
        0xC030B0F7F010B30,
        0x6882F5,
        0x0,
        0x0,
    ],
    [
        0x574A5F3F92279D1,
        0xF65745A421E32BF,
        0x55555555452152A,
        0x555555555555555,
        0x5555,
        0x0,
        0x0,
        0x0,
        0x0,
    ],
];
pub const CURVE_BB: [[[Chunk; NLEN]; 4]; 4] = [
    [
        [
            0xB306BB5E1BD810,
            0x82F5C030B0F7F01,
            0x68,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0xB306BB5E1BD80F,
            0x82F5C030B0F7F01,
            0x68,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0xB306BB5E1BD80F,
            0x82F5C030B0F7F01,
            0x68,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x5403CE8956259CF,
            0xA45BDA397B2D3E,
            0xC65DEAB2679A279,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
    ],
    [
        [
            0x1660D76BC37B01F,
            0x5EB806161EFE02,
            0xD1,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x5F343A3F37E31DE,
            0x8D3B7DD448AAC3F,
            0xC65DEAB2679A2E1,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
        [
            0x5F343A3F37E31DD,
            0x8D3B7DD448AAC3F,
            0xC65DEAB2679A2E1,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
        [
            0x5F343A3F37E31DE,
            0x8D3B7DD448AAC3F,
            0xC65DEAB2679A2E1,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
    ],
    [
        [
            0x1660D76BC37B01E,
            0x5EB806161EFE02,
            0xD1,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x1660D76BC37B01F,
            0x5EB806161EFE02,
            0xD1,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x1660D76BC37B01F,
            0x5EB806161EFE02,
            0xD1,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x1660D76BC37B01F,
            0x5EB806161EFE02,
            0xD1,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
    ],
    [
        [
            0x5F343A3F37E31DF,
            0x8D3B7DD448AAC3F,
            0xC65DEAB2679A2E1,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
        [
            0x3DA2F71D92AA9AF,
            0x45A3D4235C2F3C,
            0xC65DEAB2679A1A8,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
        [
            0x1660D76BC37B01D,
            0x5EB806161EFE02,
            0xD1,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
            0x0,
        ],
        [
            0x5F343A3F37E31DF,
            0x8D3B7DD448AAC3F,
            0xC65DEAB2679A2E1,
            0xCF1EACBE98B8E48,
            0x3C111B0EF445146,
            0xA1D8CB5307C0BBE,
            0xFFFF9EC7F01C60B,
            0xFFFFFFFFFFFFFFF,
            0xFFFFFFFF,
        ],
    ],
];

pub const USE_GLV: bool = true;
pub const USE_GS_G2: bool = true;
pub const USE_GS_GT: bool = true;
pub const GT_STRONG: bool = false;

pub const MODBYTES: usize = 64;
pub const BASEBITS: usize = 60;

pub const MODBITS: usize = 512;
pub const MOD8: usize = 3;
pub const MODTYPE: ModType = ModType::NOT_SPECIAL;
pub const SH: usize = 28;

pub const CURVETYPE: CurveType = CurveType::WEIERSTRASS;
pub const CURVE_PAIRING_TYPE: CurvePairingType = CurvePairingType::BN;
pub const SEXTIC_TWIST: SexticTwist = SexticTwist::M_TYPE;
pub const SIGN_OF_X: SignOfX = SignOfX::POSITIVEX;
pub const HASH_TYPE: usize = 32;
pub const AESKEY: usize = 16;
