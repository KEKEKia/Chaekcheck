// medals
import State_medal_best from '../../assets/images/predict_result/state_medal_best.png';
import State_medal_high from '../../assets/images/predict_result/state_medal_high.png';
import State_medal_medium from '../../assets/images/predict_result/state_medal_medium.png';
import State_medal_low from '../../assets/images/predict_result/state_medal_low.png';
// component
import Card from '../common/card';
import PredictBtn from '../common/predictBtn';

// interface
import { PredictBookInfo } from '../../interface/predictResult';
import { BtnInfo } from '../../interface/common';

function PredictResult(props: {
  predictBookInfo: PredictBookInfo;
  buttonInfo: BtnInfo;
}) {
  const { predictBookInfo, buttonInfo } = props;
  const bookInfo = predictBookInfo || {
    title: '제목',
    author: '저자',
    publisher: '출판사',
    status: '알 수 없음',
    originalPrice: '알 수 없음',
    estimatedPrice: '알 수 없음',
    coverImage: '알 수 없음',
  };
  let medal;
  switch (bookInfo.status) {
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
      break;
  }
  return (
    <Card height="h-[40rem]" width="w-[80rem]">
      <div className="Result flex justify-center align-middle">
        <div className="ResultImage h-[32rem] w-[24rem] ml-20 relative">
          <img
            src={bookInfo.coverImage}
            alt="결과 이미지"
            className="h-full w-full "
          />
          <div className="absolute -right-6 -top-3 w-[8rem] h-[10rem]">
            <img src={medal} alt="상태 메달" />
          </div>
        </div>
        <div className="ResultContents h-[32rem] w-[32rem] px-10 place-items-stretch bg-MAIN-100 ml-10 rounded-2xl p-4">
          <div className="Contents text-2xl p-4 font-bold">
            <p className=" pb-6">
              {bookInfo.title.length > 45
                ? `${bookInfo.title.substring(0, 45)}...`
                : bookInfo.title}
            </p>

            <p className="text-lg text-center font-medium pb-3">
              {bookInfo.author} | {bookInfo.publisher}
            </p>

            <div className="flex justify-around pt-4">
              <div>정상가</div>
              <div className="text-LOGO-600">예상가</div>
            </div>
            <div className="flex justify-around items-center pt-8">
              <div>{bookInfo.originalPrice} 원</div>

              <div className="text-LOGO-600">
                {bookInfo.estimatedPrice === 0
                  ? ' 매입불가'
                  : ` ${bookInfo.estimatedPrice} 원`}
              </div>
            </div>
          </div>
          <div className="RestartBtn flex justify-center h-1/4 items-end">
            <PredictBtn
              height={buttonInfo.height}
              width={buttonInfo.width}
              defaultColor={buttonInfo.defaultColor}
              selectedColor={buttonInfo.selectedColor}
              fontColor={buttonInfo.fontColor}
              action={buttonInfo.action}>
              {buttonInfo.children}
            </PredictBtn>
          </div>
        </div>
      </div>
    </Card>
  );
}

export default PredictResult;
