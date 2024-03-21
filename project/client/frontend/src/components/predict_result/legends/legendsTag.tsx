import LegendsPart from './legendsPart';

export default function LegendsTag() {
  return (
    <div className="Legends flex justify-center font-bold pt-10">
      <LegendsPart bgColor="bg-[#36A2EB] ">최상</LegendsPart>
      <LegendsPart bgColor="bg-[#4BC0C0] ">상</LegendsPart>
      <LegendsPart bgColor="bg-[#FFCE56] ">중</LegendsPart>
      <LegendsPart bgColor="bg-[#FF6384] ">매입불가</LegendsPart>
      <LegendsPart bgColor="bg-BACKGROUND-300">데이터 없음</LegendsPart>
    </div>
  );
}
