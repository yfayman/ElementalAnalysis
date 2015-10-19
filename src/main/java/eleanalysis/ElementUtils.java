/*
 * To change this license header, choose License 
 * Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This utility class has methods that return information
 * about elements. Currently, only the molar mass is returned but
 * more will be added as the program is expanded
 * @author Yan
 */


public class ElementUtils {
    
    /**
     * This enumeration contains information for different elements. At this time
     * the only data that's attached to the subtypes is the molar mass
     */
    public enum E{
        Li(6.941), Be(9.0122), B(10.811), C(12.0107), CO2(44.0095), N(14.0067),
        NO(14.0067), NO2(46.0055), F(18.9984), Ne(20.1797), Na(22.9898), Na2O(61.9789), 
        Mg(24.0350), MgO(40.3044), Al(26.9815), Al2O3(101.9613), Si(28.0855),
        SiO2(60.0843), P(30.9783), P2O5(141.9445), P2O4(125.9451), P2Ox(141.9445),
        S(32.065), SO2(64.0638), SO3(80.0632), Cl(35.4530), Cl2O3(118.9042),
        Ar(39.9480), K(39.0983), K2O(94.1960), Ca(40.0780), CaO(56.0774), 
        Sc(44.9559), Sc2O3(137.9100), Ti(47.8670), TiO2(79.8658), V(50.9415),
        V2O5(181.88), Cr(51.9961), Cr2O3(151.9904), Mn(54.9380), MnO(70.9374),
        Fe(55.8450), Fe2O3(159.6882), Co(58.9332), Co2O3(165.8646), Co2O4(181.8640),
        Co3O4(240.8), Ni(58.6934), NiO(74.6928), Cu(63.5460), CuO(79.5454), Zn(65.38),
        ZnO(81.3794), Ga(69.7230), Ga2O3(187.4442), Ge(72.64), GeO2(104.6388),
        As(74.9216), As2O3(197.8414), Se(78.96), SeO2(110.9588), Br(79.9040),
        BrO2(111.9028), Rb(85.4678), Rb2O(186.935), Sr(87.62), SrO(103.6194),
        Y(88.9059), Y2O3(225.8099), Zr(91.224), ZrO2(123.2228), Nb(92.9064),
        NbO(108.9058), Nb2O5(265.8098), Mo(95.96), MoO2(127.9588), MoO3(143.9582),
        Tc(96.9064), Tc2O7(305.8085), Ru(101.07), Ru2O(218.1394), RuO4(165.0676),
        Rh(102.9055), Rh2O3(253.8092), Pd(106.42), PdO(122.4194), Ag(107.8682),
        Ag2O(231.7358), Cd(112.4110), CdO(128.4104), In(114.8180), In2O3(277.6342),
        Sn(118.71), SnO(134.7094), SnO2(150.7088), Sb(121.76), Sb2O3(291.5182), 
        Te(127.6), TeO2(159.5988), I(126.9045), Cs(132.9055), Cs2O(281.8103),
        Ba(137.3270), BaO(153.3264), La(138.9055), La2O3(325.8091), Ce(140.1160),
        CeO2(172.1148), Pr(140.9078), Pr2O3(329.8135), Nd(144.2420), 
        Nd2O3(336.4882), Pm(144.9127), Pm2O3(337.8237), Sm(150.36), Sm2O3(348.7182),
        Eu(161.964), Eu2O3(351.9262), Gd(157.25), Gd2O3(362.4982), Tb(158.9254),
        Tb4O7(747.6972), Dy(162.5), Dy2O3(372.9982), Ho(164.9303), Ho2O3(377.8588),
        Er(167.259), Er2O3(382.5162), Tm(168.9342), Tm2O3(385.8666), Yb(173.054),
        Yb2O3(394.1062), Lu(174.9668), Lu2O3(397.9318), Hf(178.49), HfO2(210.4888),
        Ta(180.9479), Ta2O5(441.8928), W(183.84), WO2(215.8388), WO3(231.84), 
        Re(186.2070), ReO2(218.2058), Re2O7(484.4098), Os(190.23), OsO4(254.2276),
        Ir(192.217), IrO2(224.2158), Pt(195.0840), PtO2(227.0828), Au(196.9666),
        Au2O3(441.9313), Hg(200.59), HgO(216.5894), Tl(204.3833), Tl2O3(456.7648),
        Pb(207.2), PbO(223.1994), Bi(208.9804), Bi2O3(465.959), Po(208.9824),
        PoO2(240.9812), At(209.9871), At2O(435.9737), Fr(223.0197), FrO2(225.0185),
        Ra(223.0185), RaO(239.0179), Ac(227.0278), Ac2O3(502.0537), Th(232.0381),
        ThO2(264.0369), Pa(231.0359), PaO2(263.0347), Pa2O5(542.0688), U(238.0289),
        U3O8(842.0819), Np(236.0466), NpO2(268.0454), Pu(238.0496), PuO2(270.0484),
        Am(241.0568), Am2O3(530.1119);
        
        private final double molarMass;
        
        E(double mm){
            this.molarMass = mm;
        }

        public double mm(){
            return this.molarMass;
        }
    };
    
    private ElementUtils(){} // class cannot be instanced
    
   /**
    * pulls data from enum E for each respective Element/Compound
    * @param elName the element you want to measure
    * @return the molar mass an Element
    */
    public static double getMolarMass(String elName){
        double molarMass;
                
        switch(elName.trim()){
            case "Li": molarMass = E.Li.mm();
                break;
            case "Be": molarMass = E.Be.mm();
                break;
            case "B": molarMass = E.B.mm();
                break;
            case "C": molarMass = E.C.mm();
                break;
            case "CO2": molarMass = E.CO2.mm();
                break;
            case "N": molarMass = E.N.mm();
                break;
            case "NO": molarMass = E.NO.mm();
                break;
            case "NO2": molarMass = E.NO2.mm();
                break;
            case "F": molarMass = E.F.mm();
                break;
            case "Ne": molarMass = E.Ne.mm();
                break;
            case "Na": molarMass = E.Na.mm();
                break;
            case "Na2O": molarMass = E.Na2O.mm();
                break;
            case "Mg": molarMass = E.Mg.mm();
                break;
            case "MgO": molarMass = E.MgO.mm();
                break;
            case "Al": molarMass = E.Al.mm();
                break;
            case "Al2O3": molarMass = E.Al2O3.mm();
                break;
            case "Si": molarMass = E.Si.mm();
                break;
            case "SiO2": molarMass = E.SiO2.mm();
                break;
            case "P": molarMass = E.P.mm();
                break;
            case "P2O5": molarMass = E.P2O5.mm();
                break;
            case "P2O4": molarMass = E.P2O4.mm();
                break;
            case "P2OX": molarMass = E.P2Ox.mm();
                break;
            case "S": molarMass = E.S.mm();
                break;
            case "SO2": molarMass = E.SO2.mm();
                break;
            case "SO3": molarMass = E.SO3.mm();
                break;
            case "Cl": molarMass = E.Cl.mm() ;
                break;
            case "Cl2O3": molarMass = E.Cl2O3.mm();
                break;
            case "Ar": molarMass = E.Ar.mm() ;
                break;
            case "K": molarMass = E.K.mm();
                break;
            case "K2O": molarMass = E.K2O.mm();
                break;
            case "Ca": molarMass = E.Ca.mm();
                break;
            case "CaO": molarMass = E.CaO.mm();
                break;
            case "Sc": molarMass = E.Sc.mm();
                break;
            case "Sc2O3": molarMass = E.Sc2O3.mm();
                break;
            case "Ti": molarMass = E.Ti.mm();
                break;
            case "TiO2": molarMass = E.TiO2.mm();
                break;
            case "V": molarMass = E.V.mm();
                break;
            case "V2O5": molarMass = E.V2O5.mm();
                break;
            case "Cr": molarMass = E.Cr.mm();
                break;
            case "Cr2O3": molarMass = E.Cr2O3.mm();
                break;
            case "Mn": molarMass = E.Mn.mm();
                break;
            case "MnO": molarMass = E.MnO.mm();
                break;
            case "Fe": molarMass = E.Fe.mm();
                break;
            case "Fe2O3": molarMass = E.Fe2O3.mm();
                break;
            case "Co": molarMass =  E.Co.mm();
                break;
            case "Co2O3": molarMass = E.Co2O3.mm();
                break;
            case "Co2O4": molarMass = E.Co2O4.mm();
                break;
            case "Co3O4": molarMass = E.Co3O4.mm();
                break;
            case "Ni": molarMass = E.Ni.mm();
                break;
            case "NiO": molarMass = E.NiO.mm();
                break;
            case "Cu": molarMass = E.Cu.mm();
                break;
            case "CuO": molarMass = E.CuO.mm();
                break;
            case "Zn": molarMass = E.Zn.mm();
                break;
            case "ZnO": molarMass = E.ZnO.mm() ;
                break;
            case "Ga": molarMass = E.Ga.mm() ;
                break;
            case "Ga2O3": molarMass = E.Ga2O3.mm() ;
                break;
            case "Ge": molarMass = E.Ge.mm() ;
                break;
            case "GeO2": molarMass = E.GeO2.mm() ;
                break;
            case "As": molarMass = E.As.mm();
                break;
            case "As2O3": molarMass = E.As2O3.mm() ;
                break;
            case "Se": molarMass = E.Se.mm() ;
                break;
            case "SeO2": molarMass = E.SeO2.mm() ;
                break;
            case "Br": molarMass = E.Br.mm();
                break;
            case "BrO2": molarMass = E.BrO2.mm() ;
                break;
            case "Rb": molarMass = E.Rb.mm() ;
                break;
            case "Rb2O": molarMass = E.Rb2O.mm();
                break;
            case "Sr": molarMass = E.Sr.mm();
                break;
            case "SrO": molarMass = E.SrO.mm();
                break;
            case "Y": molarMass = E.Y.mm();
                break;
            case "Y2O3": molarMass = E.Y2O3.mm();
                break;
            case "Zr": molarMass = E.Zr.mm() ;
                break;
            case "ZrO2": molarMass = E.ZrO2.mm();
                break;
            case "Nb": molarMass = E.Nb.mm();
                break;
            case "NbO": molarMass = E.NbO.mm();
                break;
            case "Nb2O5": molarMass = E.Nb2O5.mm();
                break;
            case "Mo": molarMass = E.Mo.mm();
                break;
            case "MoO2": molarMass = E.MoO2.mm() ;
                break;
            case "MoO3": molarMass = E.MoO3.mm() ;
                break;
            case "Tc": molarMass = E.Tc.mm();
                break;
            case "Tc2O7": molarMass = E.Tc2O7.mm();
                break;
            case "Ru": molarMass = E.Ru.mm();
                break;
            case "Ru2O": molarMass = E.Ru2O.mm() ;
                break;
            case "RuO4": molarMass = E.RuO4.mm();
                break;
            case "Rh": molarMass = E.Rh.mm();
                break;
            case "Rh2O3": molarMass = E.Rh2O3.mm();
                break;
            case "Pd": molarMass = E.Pd.mm();
                break;
            case "PdO": molarMass = E.PdO.mm();
                break;
            case "Ag": molarMass = E.Ag.mm();
                break;
            case "Ag2O": molarMass = E.Ag2O.mm();
                break;
            case "Cd": molarMass = E.Cd.mm();
                break;
            case "CdO": molarMass = E.CdO.mm();
                break;
            case "In": molarMass = E.In.mm();
                break;
            case "In2O3": molarMass = E.In2O3.mm();
                break;
            case "Sn": molarMass = E.Sn.mm();
                break;
            case "SnO": molarMass = E.SnO.mm(); 
                break;
            case "SnO2": molarMass = E.SnO2.mm();
                break;
            case "Sb": molarMass = E.Sb.mm();
                break;
            case "Sb2O3": molarMass = E.Sb2O3.mm();
                break;
            case "Te": molarMass = E.Te.mm();
                break;
            case "TeO2": molarMass = E.TeO2.mm();
                break;
            case "I": molarMass = E.I.mm();
                break;
            case "Cs": molarMass = E.Cs.mm();
                break;
            case "Cs2O": molarMass = E.Cs2O.mm();
                break;
            case "Ba": molarMass = E.Ba.mm();
                break;
            case "BaO": molarMass = E.BaO.mm();
                break;
            case "La": molarMass = E.La.mm();
                break;
            case "La2O3": molarMass = E.La2O3.mm() ;
                break;
            case "Ce": molarMass = E.Ce.mm() ;
                break;
            case "CeO2": molarMass = E.CeO2.mm() ;
                break;
            case "Pr": molarMass = E.Pr.mm() ;
                break;
            case "Pr2O3": molarMass = E.Pr2O3.mm();
                break;
            case "Nd": molarMass = E.Nd.mm();
                break;
            case "Nd2O3": molarMass = E.Nd2O3.mm();
                break;
            case "Pm": molarMass = E.Pm.mm();
                break;
            case "Pm2O3": molarMass = E.Pm2O3.mm();
                break;
            case "Sm": molarMass = E.Sm.mm();
                break;
            case "Sm2O3": molarMass = E.Sm2O3.mm();
                break;
            case "Eu": molarMass = E.Eu.mm();
                break;
            case "Eu2O3": molarMass = E.Eu2O3.mm() ;
                break;
            case "Gd": molarMass = E.Gd.mm();
                break;
            case "Gd2O3": molarMass = E.Gd2O3.mm();
                break;
            case "Tb": molarMass = E.Tb.mm();
                break;
            case "Tb4O7": molarMass = E.Tb4O7.mm();
                break;
            case "Dy": molarMass = E.Dy.mm();
                break;
            case "Dy2O3": molarMass = E.Dy2O3.mm();
                break;
            case "Ho": molarMass = E.Ho.mm();
                break;
            case "Ho2O3": molarMass = E.Ho2O3.mm();
                break;
            case "Er": molarMass = E.Er.mm();
                break;
            case "Er2O3": molarMass = E.Er2O3.mm();
                break;
            case "Tm": molarMass = E.Tm.mm();
                break;
            case "Tm2O3": molarMass = E.Tm2O3.mm();
                break;
            case "Yb": molarMass = E.Yb.mm();
                break;
            case "Yb2O3": molarMass = E.Yb2O3.mm();
                break;
            case "Lu": molarMass = E.Lu.mm();
                break;
            case "Lu2O3": molarMass = E.Lu2O3.mm();
                break;
            case "Hf": molarMass = E.Hf.mm();
                break;
            case "HfO2": molarMass = E.HfO2.mm();
                break;
            case "Ta": molarMass = E.Ta.mm();
                break;
            case "Ta2O5": molarMass = E.Ta2O5.mm();
                break;
            case "W": molarMass = E.W.mm();
                break;
            case "WO2": molarMass = E.WO2.mm();
                break;
            case "WO3":molarMass = E.WO3.mm();
                break;
            case "Re": molarMass = E.Re.mm();
                break;
            case "ReO2": molarMass = E.ReO2.mm();
                break;
            case "Re2O7": molarMass = E.Re2O7.mm();
                break;            
            case "Os": molarMass = E.Os.mm();
                break;
            case "OsO4": molarMass = E.OsO4.mm();
                break;
            case "Ir": molarMass = E.Ir.mm();
                break;
            case "IrO2": molarMass = E.IrO2.mm();
                break;
            case "Pt": molarMass = E.Pt.mm();
                break;
            case "PtO2": molarMass = E.PtO2.mm();
                break;
            case "Au": molarMass = E.Au.mm() ;
                break;
            case "Au2O3": molarMass = E.Au2O3.mm() ;
                break;
            case "Hg": molarMass = E.Hg.mm();
                break;
            case "HgO": molarMass = E.HgO.mm();
                break;
            case "Tl": molarMass = E.Tl.mm();
                break;
            case "Tl2O3": molarMass = E.Tl2O3.mm();
                break;
            case "Pb": molarMass = E.Pb.mm();
                break;
            case "PbO": molarMass = E.PbO.mm();
                break;
            case "Bi": molarMass = E.Bi.mm();
                break;
            case "Bi2O3": molarMass = E.Bi2O3.mm();
                break;
            case "Po": molarMass = E.Po.mm();
                break;
            case "PoO2": molarMass = E.PoO2.mm();
                break;
            case "At": molarMass = E.At.mm();
                break;
            case "At2O": molarMass = E.At2O.mm();
                break;
            case "Fr": molarMass = E.Fr.mm();
                break;
            case "FrO2": molarMass = E.FrO2.mm();
                break;
            case "Ra": molarMass = E.Ra.mm() ;
                break;
            case "RaO": molarMass = E.RaO.mm();
                break;
            case "Ac": molarMass =  E.Ac.mm();
                break;
            case "Ac2O3": molarMass = E.Ac2O3.mm();
                break;
            case "Th": molarMass = E.Th.mm();
                break;
            case "ThO2": molarMass = E.ThO2.mm();
                break;
            case "Pa": molarMass = E.Pa.mm();
                break;
            case "PaO2": molarMass = E.PaO2.mm();
                break;
            case "Pa2O5": molarMass = E.Pa2O5.mm();
                break;
            case "U": molarMass = E.U.mm();
                break;
            case "U3O8": molarMass = E.U3O8.mm();
                break;
            case "Np": molarMass = E.Np.mm();
                break;
            case "NpO2": molarMass = E.NpO2.mm();
                break;
            case "Pu": molarMass = E.Pu.mm();
                break;
            case "PuO2": molarMass = E.PuO2.mm() ;
                break;
            case "Am": molarMass = E.Am.mm();
                break;
            case "Am2O3": molarMass = E.Am2O3.mm();
                break;
            
                      
                
                       
            default : System.out.println("Molar mass not found for "+ elName.trim());
                molarMass = 1;
        }
        // Gives the location of the 
       
        return molarMass;
    }
   
     // List according to SKF's presentation form
     public static final String []skf = {"Na2O", "MgO", "Al2O3", "SiO2", "P2O4", "S", "Cl", 
        "K2O", "CaO", "TiO2", "V2O5", "Cr2O3", "MnO", "Fe2O3", "Co3O4", "Ni2O3", "CuO", "ZnO",
        "As2O3", "Br", "SrO", "Y2O3", "ZrO2", "Nb2O5", "MoO3", "Rh2O3", "PdO", "Ag2O", "CdO",
        "SnO", "BaO", "WO3", "PtO2", "Au2O3", "PbO", "Bi2O3", "F"};
     
     public static final List<String> skfList = new ArrayList(Arrays.asList(skf));
     
}
