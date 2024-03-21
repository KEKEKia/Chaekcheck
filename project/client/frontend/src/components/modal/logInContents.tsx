import KakaoLogin from '../../assets/images/kakao_login/kakao_login_medium_wide.png';
import GooogleLogin from '../../assets/images/google_login/google_login_logo.png';
import CheckChaek from '../../assets/images/logo/CheckChaek.png';
import { AUTH_URI } from '../../data_source/apiInfo';

function Login() {
  const handleLogin = (url: string) => {
    window.location.href = `${AUTH_URI}/oauth2/authorization/${url}`;
  };

  return (
    <div className="LoginPart h-[680px] w-[400px] my-auto bg-BACKGROUND-50 rounded-lg shadow-md">
      <div className="LoginTitle h-1/4 ">
        <h2 className="text-center font-bold text-3xl pt-16">시작하기</h2>
      </div>
      <div className="LoginLogo h-2/5 flex justify-center ">
        <img src={CheckChaek} alt="CheckChaek" className="mx-auto py-24" />
      </div>
      <div className="py-4 flex-cols">
        <div className="LoginKakaoBtn flex justify-center pb-2">
          <button type="button" onClick={() => handleLogin('kakao')}>
            <img
              src={KakaoLogin}
              alt="KakaoLoginBtn"
              className="mx-auto shadow-lg"
            />
          </button>
        </div>
        <div className="LoginGoogleBtn flex justify-center">
          <button type="button" onClick={() => handleLogin('google')}>
            <img
              src={GooogleLogin}
              alt="GoogleLoginBtn"
              className="mx-auto shadow-lg"
            />
          </button>
        </div>
      </div>

      {/* <div className="LoginContent">
        <hr className="w-10/12 mx-auto pb-3 opacity-10  " />
        <p className="ServiceTerms text-center font-bold">서비스 약관</p>
      </div> */}
    </div>
  );
}

export default Login;
