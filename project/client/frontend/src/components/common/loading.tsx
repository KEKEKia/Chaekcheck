// icons
import LoadingBook from '../../assets/icons/loading';

function Loading() {
  return (
    <div className="LoadingPart w-screen h-screen flex justify-center items-center">
      <div className="m-auto">
        <LoadingBook />
        <div className="loading-text text-center text-lg font-bold pt-2">
          Loading...
        </div>
      </div>
    </div>
  );
}

export default Loading;
