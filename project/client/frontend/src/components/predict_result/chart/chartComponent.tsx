import { ScoreInfo } from '../../../interface/predictResult';
import ChartPart from './chartPart';

export default function ChartComponent(props: { scoreInfo: ScoreInfo }) {
  const { scoreInfo } = props;
  return (
    <div className="flex justify-around items-center">
      <ChartPart chartData={scoreInfo.back} label="책등" styleString="" />
      <ChartPart chartData={scoreInfo.cover} label="책면" styleString="" />
      <ChartPart chartData={scoreInfo.side} label="책옆" styleString="" />
    </div>
  );
}
