// interface
import { ScoreInfo } from '../../../interface/predictResult';

// component
import Card from '../../common/card';
import ChartComponent from './chartComponent';
import LegendsTag from '../legends/legendsTag';

// medals
import State_medal_best from '../../../assets/images/predict_result/state_medal_best.png';
import State_medal_high from '../../../assets/images/predict_result/state_medal_high.png';
import State_medal_medium from '../../../assets/images/predict_result/state_medal_medium.png';
import State_medal_low from '../../../assets/images/predict_result/state_medal_low.png';

export default function ResultChart(props: { scoreInfo: ScoreInfo }) {
  const { scoreInfo } = props;

  let medal;
  switch (scoreInfo.status ?? '') {
    case 'best':
      medal = State_medal_best;
      break;
    case 'high':
      medal = State_medal_high;
      break;
    case 'medium':
      medal = State_medal_medium;
      break;
    case 'low':
      medal = State_medal_low;
      break;
    default:
      medal = '';
  }
  return (
    <Card height="" width="w-[80rem]">
      <div className="text-center flex justify-center font-extrabold text-4xl mb-8 items-center">
        예측 결과 <img src={medal} alt="" className="w-16" />
      </div>
      <div className="pt-5">
        <ChartComponent scoreInfo={scoreInfo} />
        <LegendsTag />
      </div>
    </Card>
  );
}
