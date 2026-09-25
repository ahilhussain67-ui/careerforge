import { useCallback, useEffect, useState } from "react";
import AppShell from "../components/AppShell";
import ProgressBar from "../components/ProgressBar";
import { ErrorState, LoadingState } from "../components/AsyncState";
import { getLessons, getProgress, setLessonProgress } from "../services/api";

export default function LearningPage({ user, page, onNavigate, onLogout, course, onBack }) {
  const [lessons, setLessons] = useState([]);
  const [done, setDone] = useState([]);
  const [active, setActive] = useState(0);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const load = useCallback(async () => {
    setLoading(true); setError("");
    try {
      const lessonData = await getLessons(course.id);
      setLessons(lessonData); setDone([]); setActive(0); setLoading(false);
      try { const progress = await getProgress(course.id, user.id); setDone(progress.completedLessonIds || []); }
      catch { setError("Lessons loaded, but progress could not be loaded. You can still continue learning."); }
    }
    catch (err) { setError(err.message); }
    finally { setLoading(false); }
  }, [course.id, user.id]);
  useEffect(() => { const timer = setTimeout(load, 0); return () => clearTimeout(timer); }, [load]);
  if (loading) return <AppShell {...{ user, page, onNavigate, onLogout }}><LoadingState text="Preparing your lesson…" /></AppShell>;
  if (error && !lessons.length) return <AppShell {...{ user, page, onNavigate, onLogout }}><ErrorState message={error} onRetry={load} /></AppShell>;
  if (!lessons.length) return <AppShell {...{ user, page, onNavigate, onLogout }}><ErrorState message="No lessons are available for this course." onRetry={load} /></AppShell>;
  const lesson = lessons[active];
  const percent = Math.round(done.length / lessons.length * 100);
  const complete = done.includes(lesson.id);
  const toggle = async () => {
    try { setError(""); await setLessonProgress(lesson.id, user.id, !complete); setDone((current) => !complete ? [...current, lesson.id] : current.filter((id) => id !== lesson.id)); }
    catch (err) { setError(err.message); }
  };
  return <AppShell {...{ user, page, onNavigate, onLogout }}><main className="content learning"><button className="text-button back" onClick={onBack}>← All learning paths</button><section className="learning-head"><div><span className="eyebrow">{course.category}</span><h1>{course.name}</h1><p>{course.description}</p><div className="course-meta"><span>{course.difficulty}</span><span>{lessons.length} lessons</span><span>{course.estimatedDuration}</span></div></div><div className="progress-summary"><b>{percent}%</b><span>course complete</span><ProgressBar value={percent} /></div></section><div className="learning-layout"><aside className="lesson-list"><h3>Course lessons</h3>{lessons.map((item, lessonIndex) => <button key={item.id} className={lessonIndex === active ? "lesson-item active" : "lesson-item"} onClick={() => setActive(lessonIndex)}><span>{done.includes(item.id) ? "✓" : String(lessonIndex + 1).padStart(2, "0")}</span><strong>{item.title}</strong></button>)}</aside><article className="lesson-view">{error && <div className="form-error">{error}</div>}<span className="eyebrow">LESSON {active + 1} OF {lessons.length}</span><h2>{lesson.title}</h2><div className="lesson-html" dangerouslySetInnerHTML={{ __html: lesson.content }} /><div className="lesson-actions"><button className="button secondary" disabled={active === 0} onClick={() => setActive(active - 1)}>Previous</button><button className={complete ? "button secondary" : "button"} onClick={toggle}>{complete ? "Completed ✓" : "Mark complete"}</button><button className="button" disabled={active === lessons.length - 1} onClick={() => setActive(active + 1)}>Next lesson</button></div></article></div></main></AppShell>;
}
