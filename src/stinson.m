clear all
close all

%%%%% Definitionen: Schicht 1 und Material 1 ist der Luftspalt
%%%%%               Schicht 2 und Material 2 sind die Probe 
%%%%%
%%%%% Probenparameter
%%%%%

function [alpha2, alpha21] = calc (d_2, d_1, R_pore, phi)
%d_2 = 15e-3
%d_1 = 15e-4
%R_pore = 15e-3
%phi = 1
%%%%%
%%%%% Umgebungsparameter
%%%%%
T0 = 20;      % [�C] Umgebungstemperatur
P0 = 101325;  % [Pa] Luftdruck
%%%%%
%%%%% Frequenzschleife
%%%%%
f = [100:5:6300];
omega = 2*pi*f;
%%%%%
%%%%% Berechnung weiterer Umgebungsparameter
%%%%%
% Temperatur: Celsius --> Kelvin
T     = 273.16+T0;
% Universal gas constant (J.K-1.kg-1) [also 8.314 J.mol-1.K-1]
R     = 287.031;            
% Specific heat at constant pressure (J.kg-1.K-1; 260 K < T < 600 K
Cp    = 4168.8*(0.249679-7.55179e-5*T+1.69194e-7*T^2-6.46128e-11*T^3);  
% Specific heat at constant volume (J.kg-1.K-1; 260 K < T < 600 K
Cv    = Cp-R;               
% Dynamic viscosity (N.s.m-2; 100 K < T < 600 K
eta   = 7.72488e-8*T-5.95238e-11*T^2+2.71368e-14*T^3;  
% Ratio of specific heats
gamma = Cp/Cv;
% Density of air (kg.m-3)
rho0 = P0/(R*T);
% Velocity of sound (m.s^-1)
c0   = sqrt(gamma*R*T);
% Thermal conductivity  (W.m-1.K-1) - cf A. D. Pierce p 513
kappa = 2.624e-02 * ( (T/300)^(3/2) * (300+245.4*exp(-27.6/300))/(T+245.4*exp(-27.6/T)) );
% Prandtl's number
Pr    = eta*Cp/kappa;
%%%%%
%%%%% Str�mungswiderstand (Annahme: zylindrische Poren)
%%%%%
%sigma = (8*eta)/(R_pore^2 * phi);
%%%%%
%%%%% Berechnung der Hilfsgr��en
%%%%%

s = R_pore * sqrt(-j * omega * rho0 / eta);
s1 = R_pore * sqrt(-j * omega * rho0 *Cp / kappa);
J0 = besselj(0,s);
J1 = besselj(1,s);
J01 = besselj(0,s1);
J11 = besselj(1,s1);

rho_c = rho0 ./(1 -(2 .*J1 ./s ./J0)); % charakteristische Dichte der Probe
K2 = gamma *P0 ./(1+(2 .*(gamma-1) .*J11  ./s1 ./J01)); % Kompressionsmodul der Probe

k2 = omega .* sqrt(rho_c ./K2); % Wellenzahl der Probe
k1 = omega / c0; % Wellenzahl von Luft

%%%%%
%%%%% Berechnung der spezifischen Impedanzen      --> Luftspalt = 1
%%%%%                                             -->  Probe    = 2
%%%%%
Z_c1 = rho0 * c0; %charakteristische Impedanz von Luft
Z_c2 = sqrt(rho_c .*K2) ./phi; %charakteristische Impedanz der Probe

Zs1 =  -1*j * Z_c1 .* cot(k1*d_1); % Oberfl�chenimpedanz an Schicht 1 nach Allard
Zs2 =  Z_c2 .* (-1*j * Zs1 .* cot(k2*d_2) + Z_c2) ./(Zs1 - (j * Z_c2 .* cot(k2*d_2))); % Oberfl�chenimpedanz an Schicht 2 nach Allard

Zs21 =  -1*j * Z_c2 .* cot(k2*d_2);% Oberfl�chenimpedanz, wenn nur Schicht 2 w�re 


%%%%%
%%%%% Berechnung Reflexionskoeffizient
%%%%%
R1 = (Zs1-rho0*c0)./(Zs1+rho0*c0);
R2 = (Zs2-rho0*c0)./(Zs2+rho0*c0);
R21 = (Zs21-rho0*c0)./(Zs21+rho0*c0);
%%%%%
%%%%% Berechnung Schallabsorptionskoeffizient
%%%%%
alpha1  = 1 - ( abs(R1).^2);
alpha2  = 1 - ( abs(R2).^2);
alpha21  = 1 - ( abs(R21).^2);
%%%%%
%%%%% Diagramm
%%%%% mehrere Kurven plotten: nur Schicht 1, nur Schicht 2, Kombination
%%%%%
figure(1)
set(gca,'FontSize',16)
hold on
%plot(f,alpha1,'r-','Linewidth',2)
plot(f,alpha2,'g-','Linewidth',2)
plot(f,alpha21,'b-','Linewidth',2)
xlabel('Frequenz (Hz)')
ylabel('Absortionsgrad')
title('Schallabsorptionskoeffizient')
%legend([disp(d_1*1000),'mm Luftspalt'],'Probe vor Luftspalt','nur Probe')
legend('Probe vor Luftspalt','nur Probe')
%set(gca,'Ylim',[0 1])

%Alpha2 = [(f)', (alpha2)'];
%save probevorspalt.txt Alpha2;

%Alpha21 = [(f)', (alpha21)'];
%save probeohnespalt.txt Alpha21
endfunction
