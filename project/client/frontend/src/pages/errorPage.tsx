import { useNavigate } from 'react-router-dom';
import error from '../assets/images/error_page/error.png';

function ErrorPage() {
  const navigate = useNavigate();
  return (
    <div className=" h-screen w-screen bg-MAIN-200 ">
      <div className="w-full">
        <div className="flex items-center justify-center pt-[10%]">
          <img src={error} alt="error" className="w-1/2 h-[55%] " />
          <div className="text-5xl font-do-hyeon">
            <p>
              <span className="text-LOGO-500">오류</span>가 발생했습니다.
            </p>
            <p className="mt-4">걱정하지마세요.</p>
            <p className="mt-4"> 금방 해결 될거에요!</p>
          </div>
        </div>
        <button
          type="button"
          className="mx-[40%] w-[20rem] h-[4.5rem] font-do-hyeon text-2xl hover:bg-BUTTON1-400 hover:shadow-none bg-BUTTON1-200  rounded-2xl shadow-xl shadow-BACKGROUND-600 border-4 border-BUTTON1-300"
          onClick={() => navigate('/')}>
          메인으로 돌아가기
        </button>
      </div>
    </div>
  );
}

export default ErrorPage;
