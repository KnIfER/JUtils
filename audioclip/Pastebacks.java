package audioclip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ffmpeg.CMN;

public class asdasd {

	
	
	
	
	
	

	{
        //some very simple melody collections
		String xx;
		xx="[146857] B [147216] N [147442] N [147608] N [147902] B [148219] V [148557] V [148759] B [149021] N [149399] B ";
		xx="[117923] N [118130] B [118327] N [118539] B [119089] V [119506] C [120012] X ";
		
		xx="[5893] Z [6135] X [6382] C [6669] C [6967] V [7215] C [7473] X ";
		
		xx="[28944] COMMA [29221] M [29499] COMMA [29751] M [30044] N [30286] M [30619] COMMA ";
		xx="[42061] COMMA [42373] M [42589] COMMA [42883] M [43246] N [43529] M [43836] N";
		
		xx="[118083] M [118599] M [118865] N [119153] V [119309] V [119617] B [119845] C ";
		xx="[169956] X [170467] X [170704] X [171025] C [171480] C [171713] C [172085] V [172433] B [172761] V [173054] C [173402] X ";
		
		
		
		xx=" [56182] N [57007] X [57463] B [58370] Z [59076] X [59364] B [60650] Z [61266] X [61541] C [61997] Z [62502] C [62724] X [63006] C [63390] Z [65187] C [65534] B [66135] Z [67014] C [67255] X [67574] C [67869] Z [68555] X [68882] C [69542] Z [70204] X [70435] C [70717] Z [71120] C [71306] X [71596] C [72152] X [72837] C [73297] Z [73713] C [73928] X [74090] C [74438] Z [75195] Z [77121] X [77667] C [78378] Z [80751] X [81961] C [82227] X [82469] C [82934] Z [83358] X [83631] C [84095] C [84291] X [84469] C [84837] Z [85422] X [85770] V [86451] C [86701] X [86915] C [87289] Z [87812] X [88168] B [88663] Z [88875] X [89451] C [89674] X [89909] C [90382] Z [91056] X [91405] N [92115] Z [92376] X [92872] C [93089] X [93477] C [93982] X [94372] C [94643] X [94835] C [95254] Z [95820] X [96168] M [109078] C [109354] X [109599] C [109937] Z [110644] X [110931] C [111542] C [111799] X [112006] C [112335] Z [112840] X [113172] C [113486] X [113894] Z [114167] X [114580] C [115247] C [115571] C [115782] C [116014] X [116377] C [116732] V [117124] V [117517] B [118240] C [118644] V [118886] V [119072] V [119524] B [120046] B [120470] N [120924] X";

		
		xx="[422233] M [422621] COMMA [423006] M [423460] N [423899] COMMA [424323] M [424774] N [425479] N [425847] M [426155] COMMA [426557] M [426841] M [426886] N [427240] COMMA [427541] M [427926] N [428911] B [429127] N [429515] M [429873] B [430372] V [431498] C [431857] X ";
		xx="[487153] COMMA [487940] M [488328] N [488979] COMMA [489400] M [489962] N [490615] V [491062] B [491587] N [492661] V [493499] C [493827] C ";
		
		xx="[853714] N [853821] B [854012] V [854183] B [854547] C [854587] N [854946] M [854978] C [855355] N [855369] C [855682] M [855733] C [856061] X [856095] C [856414] C [856435] X [856500] Z ";
		
		xx="[976934] Z [977444] C [977677] X [977984] C [978320] Z [978939] X [979256] V";
		
		
		xx=" [1115115] COMMA [1115221] M [1115402] N [1115600] COMMA [1115790] COMMA [1116053] COMMA [1116090] M [1116332] N [1116674] B [1116835] B ";
		xx="[1122099] V [1122381] B [1122634] N [1123315] C [1123698] C ";
		
		xx="[1318164] COMMA [1318634] N [1318997] M [1320383] COMMA [1320506] COMMA [1320908] N [1321292] M [1321727] N [1322069] B [1322543] N [1322720] N ";
		
		
		xx="[1525089] C [1525307] V [1525548] C [1525780] X [1526091] Z [1526399] X [1526677] C [1527473] C [1527727] V [1528261] B [1528604] B [1529109] V [1529350] C [1529567] X";
		
	}
	
	
	
	

	public static void main(String[] args) {
		String xx="[1] Z [136] X [368] C [853] V [1377] C [1476] V [1624] B ";

		Pattern p = Pattern.compile("\\[[0-9]{1,}\\] :?[A-Z]");
		Matcher m = p.matcher(xx);
		while(m.find()) {
			CMN.Log(m.group(),m.group().split("\\] ")[1]);
		}
		
		CMN.Log(p.matcher("[1] xZ").matches());
		
		
		/////////////////////System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Long[] timExamer = new Long[]{2224000l ,2141000l ,2158000l ,2168000l ,2178000l ,929000l ,2192000l ,932000l ,2204000l ,156000l ,2228000l ,2233000l ,2254000l ,2267000l ,158000l ,1109000l ,2288000l ,2293000l ,2296000l ,2297000l ,2302000l ,2315000l ,2326000l ,2332000l ,162000l ,1116000l ,2334000l ,2348000l ,2408000l ,2416000l ,176000l ,1120000l ,2423000l ,2429000l ,2438000l ,4496000l ,4500000l ,2189464000l ,2193247000l ,179000l ,2189224000l ,2193656000l ,182000l ,1941984000l ,2189421000l ,185000l ,1941989000l ,199000l ,1941991000l ,2189227000l ,202000l ,1941993000l , 1941998000l ,2193267000l ,233000l ,1290000l ,1942001000l ,2193286000l ,236000l ,1308000l ,245000l ,1311000l ,2193281000l ,249000l ,1323000l ,252000l ,1325000l ,1942059000l ,254000l ,1329000l ,1942067000l ,2193242000l ,259000l ,1331000l ,1942080000l ,2189115000l ,2189212000l ,400000l ,1335000l ,1342000l ,1942097000l ,2227480000l ,2212210000l ,2189008000l ,2188974000l ,405000l ,1344000l ,415000l ,1362000l ,1942129000l ,2192874000l ,417000l ,1443000l ,1942132000l ,2189326000l ,2227526000l ,420000l ,1449000l ,1942133000l ,2193530000l ,424000l ,1523000l ,1942160000l ,2189334000l ,426000l ,1539000l ,1942170000l ,2189354000l ,2193278000l ,432000l ,1543000l ,1942202000l ,2189113000l ,0l ,435000l ,1546000l ,1942229000l ,2188998000l ,2189325000l ,443000l ,2188929000l ,448000l ,2212158000l ,461000l ,467000l ,1588000l ,2192877000l ,472000l ,1596000l ,477000l ,1601000l ,488000l ,1604000l ,1605000l ,2188956000l ,502000l ,1614000l ,2189402000l ,2189288000l ,2227478000l ,2213614000l ,2227471000l ,2193770000l ,2193768000l ,2189094000l ,2193656000l ,2189437000l ,7000l ,1041000l ,1941834000l ,505000l ,1618000l ,511000l ,1625000l ,1625000l ,516000l ,1630000l ,2189008000l ,521000l ,1633000l ,1633000l ,526000l ,1634000l ,540000l ,1639000l ,1645000l ,1652000l ,2188956000l ,2189217000l ,1657000l ,2188929000l ,2227540000l ,2189041000l ,111000l ,1049000l ,1941850000l ,585000l ,1661000l ,594000l ,2193513000l ,2193533000l ,2189344000l ,2189361000l ,1768000l ,2193653000l ,2189395000l ,1773000l ,1784000l ,2189309000l ,1788000l ,1803000l ,2189257000l ,2192870000l ,1806000l ,2227503000l ,1814000l ,2189292000l ,1817000l ,2189078000l ,2227536000l ,2227521000l ,2189439000l ,2189216000l ,2189011000l ,113000l ,1941898000l ,1827000l ,2189390000l ,2227525000l ,1831000l ,2193187000l ,1846000l ,2188929000l ,1856000l ,2189352000l ,1859000l ,1859000l ,2189012000l ,1862000l ,2189198000l ,1875000l ,2193645000l ,2193525000l ,1879000l ,2188929000l ,2195409000l ,2189114000l ,2227484000l ,2227475000l ,2192876000l ,2188928000l ,2189280000l ,118000l ,1052000l ,1886000l ,2193130000l ,2212159000l ,1895000l ,1907000l ,1912000l ,2212165000l ,2227511000l ,2193260000l ,2227479000l ,2227471000l ,2192873000l ,2188955000l ,2189333000l ,2227534000l ,123000l ,1941922000l ,785000l ,2192842000l ,2227490000l ,2189042000l ,2188964000l ,2189008000l ,1990000l ,2212158000l ,1995000l ,2189137000l ,2192867000l ,2006000l ,2013000l ,2030000l ,2213585000l ,2031000l ,2193642000l ,2193520000l ,2227540000l ,2227509000l ,2189212000l ,2192858000l ,2227468000l ,2189403000l ,2189448000l ,2189330000l ,2193245000l ,2227489000l ,2227507000l ,2212165000l ,2189043000l ,2193634000l ,2213589000l ,2192870000l ,2193657000l ,2189395000l ,2193756000l ,2212883000l ,2193277000l ,2227528000l ,2227460000l ,2189030000l ,2193763000l ,2212156000l ,2189122000l ,2189332000l ,2189125000l ,2189309000l ,2189115000l ,2212931000l ,2189366000l ,2212853000l ,2227527000l ,2193285000l ,2189201000l ,2212843000l ,2227534000l ,4499000l ,931000l ,2189122000l ,2227493000l ,2189042000l ,2193764000l ,2193270000l ,2193151000l ,2193272000l ,2212144000l ,2189313000l };
        ArrayList<Long> arr = new ArrayList<Long>(Arrays.asList(timExamer));
        arr.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
            	long ret = o1-o2;
                return (int) ret;//ret==0?0:ret>0?1:-1;
            }
        });
	}

}