import { useCallback, useEffect, useState } from "react";
import AppShell from "../components/AppShell";
import { ErrorState, LoadingState } from "../components/AsyncState";
import CourseCard from "../components/CourseCard";
import { getCourses, getJobs, getMyApplications, getProgress } from "../services/api";

export default function Dashboard({ user, page, onNavigate, onLogout, onOpenCourse }) {
  const [courses, setCourses] = useState([]);
  const [jobs, setJobs] = useState([]);
  const [apps, setApps] = useState([]);
  const [progress, setProgress] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const load = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      const [courseList, jobList, applicationList] = await Promise.all([
        getCourses(),
        getJobs(),
        getMyApplications(user.id)
      ]);
      const progressEntries = await Promise.all(courseList.map(async (course) => [
        course.id,
        await getProgress(course.id, user.id)
      ]));
      setCourses(courseList);
      setJobs(jobList);
      setApps(applicationList);
      setProgress(Object.fromEntries(progressEntries));
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [user.id]);

  useEffect(() => {
    const timer = setTimeout(load, 0);
    return () => clearTimeout(timer);
  }, [load]);

  const completed = Object.values(progress).reduce((sum, item) => sum + item.completedCount, 0);
  const total = courses.reduce((sum, course) => sum + (course.lessons?.length || 0), 0);
  const cards = [
    ["Learning paths", "Build job-ready technical foundations.", "skills"],
    ["Find opportunities", "Explore curated local and remote roles.", "jobs"],
    ["My applications", apps.length ? `${apps.length} application${apps.length === 1 ? "" : "s"} tracked in one place.` : "Track every role you apply for.", "applications"],
    ["Interview practice", "Improve answers before the real conversation.", "interview"],
    ["Resume builder", "Turn your experience into a polished resume.", "resume"]
  ];

  return <AppShell {...{ user, page, onNavigate, onLogout }}><main className="content">{loading ? <LoadingState text="Preparing your workspace…" /> : error ? <ErrorState message={error} onRetry={load} /> : <><section className="dashboard-hero"><div><span className="eyebrow">YOUR CAREER WORKSPACE</span><h1>Good to see you, {user.name.split(" ")[0]}.</h1><p>Make steady progress today: learn a focused lesson, practise your story, or find your next role.</p><button className="button" onClick={() => onNavigate("skills")}>Continue learning</button></div><div className="hero-stat"><b>{total ? Math.round(completed / total * 100) : 0}%</b><span>learning progress</span><small>{completed} of {total} lessons completed</small></div></section><section className="quick-grid">{cards.map(([title, text, destination]) => <button className="quick-card" key={destination} onClick={() => onNavigate(destination)}><h3>{title}</h3><p>{text}</p><span>Open →</span></button>)}</section><section className="section-head"><div><span className="eyebrow">RECOMMENDED</span><h2>Keep building momentum.</h2></div><button className="text-button" onClick={() => onNavigate("skills")}>View all learning</button></section><div className="course-grid">{courses.slice(0, 3).map((course) => <CourseCard key={course.id} course={course} progress={progress} onOpen={onOpenCourse} />)}</div><section className="section-head"><div><span className="eyebrow">OPEN ROLES</span><h2>Opportunities worth a look.</h2></div><button className="text-button" onClick={() => onNavigate("jobs")}>Browse all jobs</button></section><div className="mini-job-grid">{jobs.slice(0, 3).map((job) => <button className="mini-job" key={job.id} onClick={() => onNavigate("jobs")}><strong>{job.title}</strong><span>{job.company} · {job.location}</span></button>)}</div></>}</main></AppShell>;
}
